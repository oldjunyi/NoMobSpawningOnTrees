package com.mmyzd.nmsot.rule;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.world.biome.Biome;

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
			for (Iterator<Biome> i = Biome.REGISTRY.iterator(); i.hasNext(); ) {
				Biome biome = i.next();
				if (biome.getBiomeName().toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = Biome.getIdForBiome(biome);
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
		return Biome.getIdForBiome(entry.world.getBiomeGenForCoords(entry.pos)) == index;
	}

}
