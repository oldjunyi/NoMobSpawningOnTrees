package com.mmyzd.nmsot.rule;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.world.biome.Biome;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleBiome extends Rule {

	public int index = IntegerRange.INVALID_INT;

	public RuleBiome(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		try {
			index = IntegerRange.getInteger(s);
		} catch (Exception e) {
			String token = RuleSet.getToken(s).toLowerCase();
			for (Iterator<Biome> i = Biome.REGISTRY.iterator(); i.hasNext();) {
				Biome biome = i.next();
				if (biome.getBiomeName().toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = Biome.getIdForBiome(biome);
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
		return Biome.getIdForBiome(entry.world.getBiome(entry.pos)) == index;
	}

}
