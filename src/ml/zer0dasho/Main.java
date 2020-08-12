package ml.zer0dasho;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;
import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.CSUtility;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import com.sn1cko.actionbar.actionbar;
import com.sn1cko.actionbar.methods.theAction;

public class Main extends JavaPlugin implements Listener {

	public static final Map<UUID, String> delays = Maps.newHashMap();
	public static final Map<UUID, Integer> delay_times = Maps.newHashMap();

	private static final boolean DEV_BUILD = false;
	private static final String DELAY = "%s.Shooting.Weapon_Delay";
	
	private static CSUtility util;
	
	@Override
	public void onEnable() {
		Main.util = new CSUtility();
		Bukkit.getPluginManager().registerEvents(this, this);
		
		for(Player player : Bukkit.getOnlinePlayers())
			delayWeapon(player, player.getInventory().getItemInHand());
	}

	@EventHandler
	public void pickupEvent(PlayerPickupItemEvent e) {
		log("PICKED UP ITEM");
		delayWeapon(e.getPlayer(), e.getItem().getItemStack());
	}
	
	@EventHandler
	public void itemHeld(PlayerItemHeldEvent e) {
		log("SWITCHED HANDS");
		delayWeapon(e.getPlayer(),e.getPlayer().getInventory().getItem(e.getNewSlot()));
	}
	
	private void delayWeapon(Player player, ItemStack item) {
		String currentWeapon = util.getWeaponTitle(item);
		
		if(currentWeapon == null) {
			delays.put(player.getUniqueId(), null);
			return;
		}
		
		Integer delay = CSDirector.ints.get(String.format(DELAY, currentWeapon));
		if(delay == null) delay = 0;
		
		String id = UUID.randomUUID().toString();
		WeaponDelay weaponDelay = new WeaponDelay(player.getUniqueId(), id, delay);
		
		log("TO WEAPON: " + currentWeapon + " WITH DELAY: " + delay);
		delays.put(player.getUniqueId(), id);
		weaponDelay.runTaskTimer(this, 0L, 1L);
	}
	
	@EventHandler
	public void beforeShoot(WeaponPrepareShootEvent e) {
		log("PREPARING TO SHOOT!");
		
		if(delays.get(e.getPlayer().getUniqueId()) != null) {
			log("CANCELLED SHOT!");

			Integer delay = delay_times.get(e.getPlayer().getUniqueId());
			theAction.sendAction(e.getPlayer(), "&cThis weapon has a warm up of " + (delay/20) + " seconds.", actionbar.getPlugin(), true);
			
			log("DELAY IS: " + delay + " OR: " + (delay/20));
			
			e.setCancelled(true);
		}
	}
	
	public static void log(String message) {
		if(DEV_BUILD)
			System.out.println(message);
	}
}
