package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

<<<<<<< HEAD
import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.EnumCreatureType;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
=======
import net.minecraft.entity.EnumCreatureType;

>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
import com.mmyzd.nmsot.SpawningEntry;

public class RuleMobType extends Rule {

<<<<<<< HEAD
	public EnumCreatureType type = null;
	
	public RuleMobType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getToken(s).toLowerCase();
		if (name.equals("monster")) name = "monster";
		if (name.equals("animal") || name.equals("creature")) name = "creature";
		if (name.equals("ambient")) name = "ambient";
		if (name.equals("water") || name.equals("watercreature")) name = "waterCreature";
		try {
			type = EnumCreatureType.valueOf(name);
		} catch (Exception e) {
		}
		if (type == null) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such mob type: " + name);
		}
=======
	EnumCreatureType type;
	
	public RuleMobType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getIdentifier(s, "mob type");
		if (name.equalsIgnoreCase("MONSTER")) name = "MONSTER";
		if (name.equalsIgnoreCase("ANIMAL")|| name.equalsIgnoreCase("CREATURE")) name = "CREATURE";
		if (name.equalsIgnoreCase("AMBIENT")) name = "AMBIENT";
		if (name.equalsIgnoreCase("WATER") || name.equalsIgnoreCase("WATER_CREATURE")) name = "WATER_CREATURE";
		type = EnumCreatureType.valueOf(name);
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
<<<<<<< HEAD
		return type == null ? false : entry.entity.isCreatureType(type, false);
=======
		return entry.entity.isCreatureType(type, false);
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	}

}
