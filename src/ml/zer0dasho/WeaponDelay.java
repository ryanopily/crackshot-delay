package ml.zer0dasho;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WeaponDelay extends BukkitRunnable implements Listener {

	private UUID player;
	private String id;
	private int delay;
	
	public WeaponDelay(UUID player, String id, int delay) {
		this.player = player;
		this.id = id;
		this.delay = delay;
		
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
	}
	
	@Override
	public void run() {
		Main.delay_times.put(player, delay);
		
		if(Main.delays.get(player) != id) {
			HandlerList.unregisterAll(this);
			this.cancel();
		}
		
		if(delay-- <= 0) {
			Main.delays.put(player, null);
			HandlerList.unregisterAll(this);
			this.cancel();
		}
	}
	
	@EventHandler
	public void switchHands(PlayerItemHeldEvent e) {
		if(e.getPlayer().getUniqueId().equals(player)) {
			HandlerList.unregisterAll(this);
			this.cancel();
		}
	}
}