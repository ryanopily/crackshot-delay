# crackshot-delay

**crackshot-delay** is a **Bukkit/Spigot** add-on to **CrackShot**, a weapons plugin.
It adds a key in a weapon's YAML config, *Weapon_Delay*, which adds a pre-fire delay to the specified weapon.
Basically, if a player switches to a new weapon, they have to wait a specified amount of time before firing that weapon.
# Version
This plugin has only been tested on a **Spigot** server running **1.8.8**, and was built for **1.8.8**
# Dependencies
**crackshot-delay** has one dependency, **[Actionbar](https://dev.bukkit.org/projects/actionbar)**.
It's used to alert the player about the delay on certain weapons.
# Config
**crackshot-delay** doesn't have a config - instead, it adds a key to any weapon configuration for **CrackShot**.
That key is **yourweapon.Shooting.Weapon_Delay**, and it should be an integer value, in ticks (20 ticks = 1 second).
