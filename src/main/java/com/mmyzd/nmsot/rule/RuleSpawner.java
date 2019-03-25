package com.mmyzd.nmsot.rule;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleSpawner extends Rule {

    @Override
    public boolean apply(SpawningEntry entry) {
        return entry.fromSpawner;
    }

}
