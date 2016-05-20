package com.mmyzd.nmsot.rule;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleNot extends Rule {
	
<<<<<<< HEAD
	public Rule rule = null;
=======
	Rule rule;
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	
	public RuleNot(Rule rule) {
		this.rule = rule;
	}
	
	public boolean apply(SpawningEntry entry) {
		return !rule.apply(entry);
	}
	
}
