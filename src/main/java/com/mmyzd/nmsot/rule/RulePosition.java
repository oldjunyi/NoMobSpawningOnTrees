package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.SpawningEntry;

public class RulePosition extends Rule {
	
	private static final int INF = 300000000;
	private int lhsX = -INF, rhsX = +INF;
	private int lhsY = -INF, rhsY = +INF;
	private int lhsZ = -INF, rhsZ = +INF;
	
	public RulePosition(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		IntegerRange rangeX = IntegerRange.parse(RuleSet.getToken(s));
		RuleSet.getTokenEqualsIgnoreCase(s, ",");
		IntegerRange rangeY = IntegerRange.parse(RuleSet.getToken(s));
		RuleSet.getTokenEqualsIgnoreCase(s, ",");
		IntegerRange rangeZ = IntegerRange.parse(RuleSet.getToken(s));
		lhsX = rangeX.lhs;
		lhsY = rangeY.lhs - 1;
		lhsZ = rangeZ.lhs;
		rhsX = rangeX.rhs;
		rhsY = rangeY.rhs - 1;
		rhsZ = rangeZ.rhs;
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.x >= lhsX && entry.x <= rhsX
			&& entry.y >= lhsY && entry.y <= rhsY
			&& entry.z >= lhsZ && entry.z <= rhsZ;
	}

}
