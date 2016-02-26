package com.mmyzd.nmsot.rule;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleWood extends Rule {

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block.isWood(entry.world, entry.x, entry.y, entry.z);
	}

}
