package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.SpawningEntry;

public class RulePosition extends Rule {

	private int lhsX, rhsX;
	private int lhsY, rhsY;
	private int lhsZ, rhsZ;

	public RulePosition(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		IntegerRange rangeX = new IntegerRange(s);
		RuleSet.getTokenEqualsIgnoreCase(s, ",");
		IntegerRange rangeY = new IntegerRange(s);
		RuleSet.getTokenEqualsIgnoreCase(s, ",");
		IntegerRange rangeZ = new IntegerRange(s);
		lhsX = rangeX.lhs;
		lhsY = rangeY.lhs - 1;
		lhsZ = rangeZ.lhs;
		rhsX = rangeX.rhs;
		rhsY = rangeY.rhs - 1;
		rhsZ = rangeZ.rhs;
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return (lhsX <= entry.x && entry.x <= rhsX) && (lhsY <= entry.y && entry.y <= rhsY)
				&& (lhsZ <= entry.z && entry.z <= rhsZ);
	}

}
