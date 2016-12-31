package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.EnumCreatureType;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleMobType extends Rule {

	public EnumCreatureType type = null;

	public RuleMobType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getToken(s).toLowerCase();
		if (name.equals("monster")) {
			type = EnumCreatureType.MONSTER;
		}
		if (name.equals("animal") || name.equals("creature")) {
			type = EnumCreatureType.CREATURE;
		}
		if (name.equals("ambient")) {
			type = EnumCreatureType.AMBIENT;
		}
		if (name.equals("water") || name.equals("watercreature") || name.equals("water_creature")) {
			type = EnumCreatureType.WATER_CREATURE;
		}
		if (type == null) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such mob type: " + name);
		}
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return type == null ? false : entry.entity.isCreatureType(type, false);
	}

}
