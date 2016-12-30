package com.mmyzd.nmsot.rule;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleNot extends Rule {

	public Rule rule = null;

	public RuleNot(Rule rule) {
		this.rule = rule;
	}

	public boolean apply(SpawningEntry entry) {
		return !rule.apply(entry);
	}

}
