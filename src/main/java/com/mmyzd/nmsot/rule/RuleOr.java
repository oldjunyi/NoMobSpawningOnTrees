package com.mmyzd.nmsot.rule;

import java.util.ArrayList;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleOr extends Rule {
	
<<<<<<< HEAD
	private Rule[] rule = null;
=======
	Rule[] rule;
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	
	public RuleOr(ArrayList<Rule> list) {
		int n = list.size();
		rule = new Rule[n];
		list.toArray(rule);
	}
	
	public boolean apply(SpawningEntry entry) {
		int n = rule.length;
		for (int i = 0; i < n; i++)
			if (rule[i].apply(entry)) return true; 
		return false;
	}
	
}
