package com.mmyzd.nmsot.rule;

import java.util.HashSet;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class RuleBiomeType extends Rule {

	public HashSet<Integer> biomeIDs = new HashSet<Integer>();

	public RuleBiomeType(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String token = RuleSet.getToken(s).toUpperCase();
		Type type = BiomeDictionary.Type.getType(token);
		Biome[] biomes = BiomeDictionary.getBiomesForType(type);
		for (Biome biome : biomes) {
			biomeIDs.add(Biome.getIdForBiome(biome));
		}
		if (biomeIDs.isEmpty()) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No biome in biome type: " + token);
		}

	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return biomeIDs.contains(Biome.getIdForBiome(entry.world.getBiomeForCoordsBody(entry.pos)));
	}

}
