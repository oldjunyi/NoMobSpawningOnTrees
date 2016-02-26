package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.entity.EnumCreatureType;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleMobType extends Rule {

	EnumCreatureType type;
	
	public RuleMobType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getIdentifier(s, "mob type");
		if (name.equalsIgnoreCase("monster")) name = "monster";
		if (name.equalsIgnoreCase("animal") || name.equalsIgnoreCase("creature")) name = "creature";
		if (name.equalsIgnoreCase("ambient")) name = "ambient";
		if (name.equalsIgnoreCase("water") || name.equalsIgnoreCase("waterCreature")) name = "waterCreature";
		type = EnumCreatureType.valueOf(name);
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.entity.isCreatureType(type, false);
	}

}
