package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

import com.mmyzd.nmsot.SpawningEntry;

public class RulePosition extends Rule {
	
	int[] lhs = new int[3];
	int[] rhs = new int[3];
	
	public RulePosition(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String[] pos = RuleSet.getToken(s).split(",");
		if (pos.length != 3) throw new Exception("Invalid coordinate");
		for (int i = 0; i < 3; i++) {
			lhs[i] = 0;
			rhs[i] = 1048576;
			if (pos[i].equals("*")) continue;
			String[] u = pos[i].split("-");
			if (u.length < 1 || u.length > 2) throw new Exception("Invalid coordinate");
			for (int j = 0; j < u.length; j++)
				if (!StringUtils.isNumeric(u[j]) || u[j].length() > 18)
					throw new Exception("Invalid coordinate");
			lhs[i] = (int)Math.min(Long.parseLong(u[0]), rhs[i]);
			if (u.length == 1) rhs[i] = lhs[i];
			if (u.length == 2) rhs[i] = (int)Math.min(Long.parseLong(u[1]), rhs[i]);
			if (lhs[i] > rhs[i]) {
				int at = lhs[i];
				lhs[i] = rhs[i];
				rhs[i] = at;
			}
		}
		lhs[1]--;
		rhs[1]--;
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.x >= lhs[0] && entry.x <= rhs[0]
			&& entry.y >= lhs[1] && entry.y <= rhs[1]
			&& entry.z >= lhs[2] && entry.z <= rhs[2];
	}

}
