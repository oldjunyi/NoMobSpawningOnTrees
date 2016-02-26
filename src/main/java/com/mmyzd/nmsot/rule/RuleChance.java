package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleChance extends Rule {
	
	double prob;
	
	public RuleChance(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String tmp = RuleSet.getToken(s);
		try {
			prob = Double.parseDouble(tmp);
		} catch (Exception e) {
			throw new Exception("Invalid probability");
		}
		if (prob < 0 || prob > 1) throw new Exception("Probability should fit in 0.0 ~ 1.0");
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.rand.nextDouble() < prob;
	}

}
