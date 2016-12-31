package com.mmyzd.nmsot;

import java.io.File;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.rule.RuleSet;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		if (event.getModID().equals(NoMobSpawningOnTrees.MODID))
			update();
	}

	void reload() {
		file.load();

		extraSpawningTries = file.get("general", "extraSpawningTries", 0.0);
		extraSpawningTries.setComment("Extra spawning tries per tick. This will only applies to hostile mobs.");

		spawnCapacityMonster = file.get("general", "spawnCapacityMonster", -1);
		spawnCapacityAnimal = file.get("general", "spawnCapacityAnimal", -1);
		spawnCapacityAmbient = file.get("general", "spawnCapacityAmbient", -1);
		spawnCapacityWater = file.get("general", "spawnCapacityWater", -1);

		String comment = "The capacity is the maximum (approximate) number of creatures can exist near the player.";
		comment += "\nSet -1 to use the default value, which is: monster(70), animal(10), ambient(15), water(5)";
		spawnCapacityAmbient.setComment(comment);

		blacklistRules = file.get("general", "blacklistRules",
				new String[] { "woodlogs    # This line disables mob spawning on all types of wood logs.",
						"block:minecraft:brown_mushroom_block || block:minecraft:red_mushroom_block",
						"# The above line disables mob spawning on mushroom blocks.", });
		blacklistRules.setComment("For details, please check https://github.com/oldjunyi/NoMobSpawningOnTrees/wiki");

		update();
	}

	public void update() {
		extraSpawningTries.set(Math.min(Math.max(extraSpawningTries.getDouble(0.0), 0), 10000));
		spawnCapacityMonster.set(Math.min(Math.max(spawnCapacityMonster.getInt(), -1), 10000));
		spawnCapacityAnimal.set(Math.min(Math.max(spawnCapacityAnimal.getInt(), -1), 10000));
		spawnCapacityAmbient.set(Math.min(Math.max(spawnCapacityAmbient.getInt(), -1), 10000));
		spawnCapacityWater.set(Math.min(Math.max(spawnCapacityWater.getInt(), -1), 10000));
		updateSpawnCapacity(EnumCreatureType.MONSTER, spawnCapacityMonster.getInt());
		updateSpawnCapacity(EnumCreatureType.CREATURE, spawnCapacityAnimal.getInt());
		updateSpawnCapacity(EnumCreatureType.AMBIENT, spawnCapacityAmbient.getInt());
		updateSpawnCapacity(EnumCreatureType.WATER_CREATURE, spawnCapacityWater.getInt());
		if (NoMobSpawningOnTrees.instance.rules != null) {
			NoMobSpawningOnTrees.instance.rules = new RuleSet(blacklistRules.getStringList());
		}
		file.save();
	}

	HashMap<String, Integer> capacityBackup = new HashMap<String, Integer>();

	void updateSpawnCapacity(EnumCreatureType e, int cur) {
		try {
			if (cur == -1) {
				Integer old = capacityBackup.get(e.name());
				if (old == null) {
					return;
				}
				ObfuscationReflectionHelper.setPrivateValue(EnumCreatureType.class, e, old, "maxNumberOfCreature",
						"field_75606_e");
				capacityBackup.remove(e.name());
			} else {
				Integer old = capacityBackup.get(e.name());
				if (old == null) {
					capacityBackup.put(e.name(), e.getMaxNumberOfCreature());
				}
				ObfuscationReflectionHelper.setPrivateValue(EnumCreatureType.class, e, cur, "maxNumberOfCreature",
						"field_75606_e");
			}
		} catch (Exception c) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("Failed to set spawn capacity for " + e.name());
		}
	}

}
