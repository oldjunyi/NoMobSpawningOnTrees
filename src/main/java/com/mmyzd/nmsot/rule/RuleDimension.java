package com.mmyzd.nmsot.rule;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleDimension extends Rule {

	private int index = IntegerRange.INVALID_INT;

	public RuleDimension(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		try {
			index = IntegerRange.getInteger(s);
		} catch (Exception e) {
			String token = RuleSet.getToken(s).toLowerCase();
			for (WorldServer world : DimensionManager.getWorlds()) {
				if (world.provider.getDimensionType().getName().toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = world.provider.getDimension();
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
		return entry.world.provider.getDimension() == index;
	}

}
