package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.entity.Entity;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleMob extends Rule {

	String name, pfxn;
	Class<? extends Entity> ret;
	
	public RuleMob(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		name = RuleSet.getIdentifier(s, "mob class name");
		pfxn = "Entity" + name;
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		Class<? extends Entity> c = entry.entity.getClass();
		if (ret != null) return c == ret;
		String mob = c.getSimpleName();
		if (mob.equals(name) || mob.equalsIgnoreCase(pfxn)) {
			ret = c;
			return true;
		}
		return false;
	}

}
