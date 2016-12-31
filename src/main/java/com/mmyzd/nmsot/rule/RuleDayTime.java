package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleDayTime extends Rule {

	private int lhs, rhs;

	public RuleDayTime(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		IntegerRange range = new IntegerRange(s);
		lhs = range.lhs;
		rhs = range.rhs;
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		long time = entry.world.getWorldTime();
		return lhs <= time && time <= rhs;
	}

}
