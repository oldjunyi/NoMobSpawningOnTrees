package com.mmyzd.nmsot;

import java.io.File;
import java.util.HashMap;

import com.mmyzd.nmsot.rule.RuleSet;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {
	
	public Configuration file;
	
	public Property extraSpawningTries;
	public Property spawnCapacityMonster;
	public Property spawnCapacityAnimal;
	public Property spawnCapacityAmbient;
	public Property spawnCapacityWater;
	public Property blacklistRules;
	
	public ConfigManager(File configDir) {
		file = new Configuration(new File(configDir, "NoMobSpawningOnTrees.cfg"));
		reload();
	}
	
	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(NoMobSpawningOnTrees.MODID)) {
			update();
			if (NoMobSpawningOnTrees.instance.rules != null)
				NoMobSpawningOnTrees.instance.rules = new RuleSet(blacklistRules.getStringList());
		}
	}
	
	void reload() {
		file.load();
		
		extraSpawningTries = file.get("general", "extraSpawningTries", 0.0);
		extraSpawningTries.comment = "Extra spawning tries per tick. This will only applies to hostile mobs.";
		
		spawnCapacityMonster = file.get("general", "spawnCapacityMonster", -1);
		spawnCapacityAnimal  = file.get("general", "spawnCapacityAnimal" , -1);
		spawnCapacityAmbient = file.get("general", "spawnCapacityAmbient", -1);
		spawnCapacityWater   = file.get("general", "spawnCapacityWater"  , -1);
		
		spawnCapacityAmbient.comment = "The capacity is the maximum (approximate) number of creatures can exist near the player.";
		spawnCapacityAmbient.comment += "\nSet -1 to use the default value, which is: monster(70), animal(10), ambient(15), water(5)";
		
		blacklistRules = file.get("general", "blacklistRules", new String[] {
			"woodlogs    # This line disables mob spawning on all types of wood logs.",
			"block:minecraft:brown_mushroom_block || block:minecraft:red_mushroom_block",
			"# The above line disables mob spawning on mushroom blocks.",
			"# Also, it shows you can use operators when defining rules:",
			"#   not := \"~\" or \"!\"",
			"#   and := \"&\" or \"&&\"",
			"#   or  := \"|\" or \"||\"",
			"#   parentheses := \"(\" or \")\"",
			"# If the first character of any rule is \"-\", the rule will be treated as whitelist.",
			"# For example, \"-mob:EntityCreeper\" or \"-mob:creeper\" allows creeper to spawn.",
			"# The rules are applied one by one, if there is conflict between blacklist and whitelist,",
			"# the latter rule will override the former rule."
		});
		blacklistRules.comment = "For details, please check https://github.com/oldjunyi/NoMobSpawningOnTrees/wiki";
		
		update();
	}
	
	public void update() {
		extraSpawningTries.set(Math.min(Math.max(extraSpawningTries.getDouble(0.0), 0), 10000));
		spawnCapacityMonster.set(Math.min(Math.max(spawnCapacityMonster.getInt(), -1), 10000));
		spawnCapacityAnimal .set(Math.min(Math.max(spawnCapacityAnimal .getInt(), -1), 10000));
		spawnCapacityAmbient.set(Math.min(Math.max(spawnCapacityAmbient.getInt(), -1), 10000));
		spawnCapacityWater  .set(Math.min(Math.max(spawnCapacityWater  .getInt(), -1), 10000));
		updateSpawnCapacity("monster", spawnCapacityMonster.getInt());
		updateSpawnCapacity("creature", spawnCapacityAnimal.getInt());
		updateSpawnCapacity("ambient", spawnCapacityAmbient.getInt());
		updateSpawnCapacity("waterCreature", spawnCapacityWater.getInt());
		file.save();
	}
	
	HashMap<String, Integer> capacityBackup = new HashMap<String, Integer>();
	
	void updateSpawnCapacity(String name, int cur) {
		EnumCreatureType e = EnumCreatureType.valueOf(name);
		if (cur == -1) {
			Integer old = capacityBackup.get(name);
			if (old == null) return;
			ObfuscationReflectionHelper.setPrivateValue(EnumCreatureType.class, e, old, "maxNumberOfCreature", "field_75606_e");
			capacityBackup.remove(name);
		} else {
			Integer old = capacityBackup.get(name);
			if (old == null) capacityBackup.put(name, e.getMaxNumberOfCreature());
			ObfuscationReflectionHelper.setPrivateValue(EnumCreatureType.class, e, cur, "maxNumberOfCreature", "field_75606_e");
		}
	}
	
}
