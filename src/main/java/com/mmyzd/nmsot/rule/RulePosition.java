package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

import com.mmyzd.nmsot.SpawningEntry;

public class RulePosition extends Rule {
	
	int lhsX, rhsX;
	int lhsY, rhsY;
	int lhsZ, rhsZ;
	
	public RulePosition(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String[] pos = RuleSet.getToken(s).split(",");
		if (pos.length != 3) throw new Exception("Invalid coordinate");
		IntegerRange rangeX = IntegerRange.parse(pos[0]);
		IntegerRange rangeY = IntegerRange.parse(pos[0]);
		IntegerRange rangeZ = IntegerRange.parse(pos[0]);
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
