package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class RuleDimension extends Rule {

	private int index = IntegerRange.INVALID_INT;

	public RuleDimension(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		try {
			index = IntegerRange.getInteger(s);
		} catch (Exception e) {
			String token = RuleSet.getToken(s).toLowerCase();
			for (WorldServer world : DimensionManager.getWorlds()) {
				if (world.provider.getDimensionName().toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = world.provider.getDimensionId();
					break;
				}
			}
			if (index == IntegerRange.INVALID_INT) {
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such dimension ID or name: " + token);
			}
		}
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.provider.getDimensionId() == index;
	}

}
