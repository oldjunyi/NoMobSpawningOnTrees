package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

import net.minecraft.world.biome.BiomeGenBase;

public class RuleBiome extends Rule {

	public int index = IntegerRange.INVALID_INT;

	public RuleBiome(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		try {
			index = IntegerRange.getInteger(s);
		} catch (Exception e) {
			String token = RuleSet.getToken(s).toLowerCase();
			for (BiomeGenBase biome: BiomeGenBase.getBiomeGenArray()) {
				if (biome.biomeName.toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = biome.biomeID;
					break;
				}
			}
			if (index == IntegerRange.INVALID_INT) {
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such biome ID or name: " + token);
			}
		}
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.getBiomeGenForCoordsBody(entry.pos).biomeID == index;
	}

}
