package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

<<<<<<< HEAD
import com.mmyzd.nmsot.IntegerRange;
=======
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
import com.mmyzd.nmsot.SpawningEntry;

public class RulePosition extends Rule {
	
<<<<<<< HEAD
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
=======
	int lhsX, rhsX;
	int lhsY, rhsY;
	int lhsZ, rhsZ;
	
	public RulePosition(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String[] pos = RuleSet.getToken(s).split(",");
		if (pos.length != 3) throw new Exception("Invalid coordinate");
		IntegerRange rangeX = IntegerRange.parse(pos[0]);
		IntegerRange rangeY = IntegerRange.parse(pos[1]);
		IntegerRange rangeZ = IntegerRange.parse(pos[2]);
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
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
