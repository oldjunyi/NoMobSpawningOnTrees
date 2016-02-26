package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.entity.EnumCreatureType;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleMobType extends Rule {

	public EnumCreatureType type;
	
	public RuleMobType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getIdentifier(s, "mob type");
		if (name.equalsIgnoreCase("MONSTER")) name = "MONSTER";
		if (name.equalsIgnoreCase("ANIMAL")|| name.equalsIgnoreCase("CREATURE")) name = "CREATURE";
		if (name.equalsIgnoreCase("AMBIENT")) name = "AMBIENT";
		if (name.equalsIgnoreCase("WATER") || name.equalsIgnoreCase("WATER_CREATURE")) name = "WATER_CREATURE";
		type = EnumCreatureType.valueOf(name);
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.entity.isCreatureType(type, false);
	}

}
