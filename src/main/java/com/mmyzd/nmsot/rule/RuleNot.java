package com.mmyzd.nmsot.rule;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleNot extends Rule {
	
	Rule rule;
	
	public RuleNot(Rule rule) {
		this.rule = rule;
	}
	
	public boolean apply(SpawningEntry entry) {
		return !rule.apply(entry);
	}
	
}
