package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleDimension extends Rule {

	int index;
	
	public RuleDimension(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String tmp = RuleSet.getToken(s);
		try {
			index = Integer.parseInt(tmp);
		} catch (Exception e) {
			throw new Exception("Invalid dimension ID");
		}
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.provider.dimensionId == index;
	}

}
