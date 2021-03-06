package com.mmyzd.nmsot.rule;

import java.util.ArrayList;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleAnd extends Rule {

	private Rule[] rule = null;

	public RuleAnd(ArrayList<Rule> list) {
		int n = list.size();
		rule = new Rule[n];
		list.toArray(rule);
	}

	public boolean apply(SpawningEntry entry) {
		int n = rule.length;
		for (int i = 0; i < n; i++) {
			if (!rule[i].apply(entry)) {
				return false;
			}
		}
		return true;
	}

}
