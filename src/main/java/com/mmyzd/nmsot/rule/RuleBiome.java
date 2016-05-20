package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.world.biome.BiomeGenBase;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleBiome extends Rule {

	public int index = -1;
	
	public RuleBiome(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String token = RuleSet.getToken(s).toLowerCase();
		String indexString = IntegerRange.getIntegerStringFrom(token, 0);
		if (IntegerRange.validateIntegerLength(indexString) && token.equals(indexString)) {
			index = Integer.parseInt(indexString);
		} else {
			for (BiomeGenBase biome: BiomeGenBase.getBiomeGenArray()) {
				if (biome.biomeName.toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = biome.biomeID;
					break;
				}
			}
		}
		if (index == -1) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such biome ID or name: " + token);
		}
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.getBiomeGenForCoords(entry.x, entry.z).biomeID == index;
	}

}
