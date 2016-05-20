package com.mmyzd.nmsot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;

import com.mmyzd.nmsot.rule.RuleBiome;
import com.mmyzd.nmsot.rule.RuleBiomeType;
import com.mmyzd.nmsot.rule.RuleMob;
import com.mmyzd.nmsot.rule.RuleMobType;
import com.mmyzd.nmsot.rule.RuleSet;

public class SpawnListManager {
	
	public static final class SpawnEntry {
		public boolean add = true;
		public int weight, countMin, countMax;
		public EnumCreatureType type;
		public Biome biome;
		public Class<? extends Entity> mob;
	}
	
	private static ArrayList<SpawnEntry> entries = new ArrayList<SpawnEntry>();
	
	public static void parse(LinkedList<Character> s) throws Exception {
		int weight = 5, countMin = 1, countMax = 2;
		EnumCreatureType type = EnumCreatureType.MONSTER;
		HashSet<Biome> biomes = new HashSet<Biome>();
		HashSet<Class<? extends Entity>> mobs = new HashSet<Class<? extends Entity>>();
		while (true) {
			String token = RuleSet.getToken(s);
			if (token.equalsIgnoreCase("biome")) {
				RuleBiome biome = new RuleBiome(s);
				if (biome.index != -1) biomes.add(Biome.getBiome(biome.index));
			} else if (token.equalsIgnoreCase("biometype")) {
				RuleBiomeType biometype = new RuleBiomeType(s);
				for (Integer index: biometype.biomeIDs) {
					biomes.add(Biome.getBiome(index));
				}
			} else if (token.equalsIgnoreCase("mob")) {
				RuleMob mob = new RuleMob(s);
				if (mob.entityClasses != null) mobs.addAll(mob.entityClasses);
				if (mob.entityClass != null) mobs.add(mob.entityClass);
			} else if (token.equalsIgnoreCase("weight")) {
				RuleSet.nextPart(s);
				try {
					weight = Integer.parseInt(RuleSet.getToken(s));
				} catch (Exception e) {
					throw new Exception("Invalid weight");
				}
			} else if (token.equalsIgnoreCase("count")) {
				RuleSet.nextPart(s);
				IntegerRange range = IntegerRange.parse(RuleSet.getToken(s));
				countMin = range.lhs;
				countMax = range.rhs;
			}  else if (token.equalsIgnoreCase("mobtype")) {
				RuleMobType mobtype = new RuleMobType(s);
				if (mobtype.type != null) type = mobtype.type;
			} else {
				throw new Exception("Invalid tag <" + token + ">");
			}
			if (!RuleSet.getTokenEqualsIgnoreCase(s, ",")) break;
		}
		if (RuleSet.skipSpace(s) != '#') throw new Exception("Syntax error");
		for (Class<? extends Entity> mob: mobs) {
			for (Biome biome : biomes) {
				boolean modified = false;
				@SuppressWarnings("unchecked")
				List<Biome.SpawnListEntry> spawns = biome.getSpawnableList(type);
				for (Biome.SpawnListEntry entry: spawns) {
					if (entry.entityClass == mob) {
						SpawnEntry e = new SpawnEntry();
						e.biome = biome;
						e.mob = mob;
						e.type = type;
						e.weight = entry.itemWeight;
						e.countMin = entry.minGroupCount;
						e.countMax = entry.maxGroupCount;
						e.add = false;
						entries.add(e);
						e = new SpawnEntry();
						e.biome = biome;
						e.mob = mob;
						e.type = type;
						e.weight = entry.itemWeight = weight;
						e.countMin = entry.minGroupCount = countMin;
						e.countMax = entry.maxGroupCount = countMax;
						entries.add(e);
						LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("  modified spawn entry " + e.mob.getSimpleName() + ": " + weight + ", " + countMin + ", " + countMax + " at " + biome.getBiomeName());
						modified = true;
					}
				}
				if (!modified) {
					SpawnEntry e = new SpawnEntry();
					e.biome = biome;
					e.mob = mob;
					e.type = type;
					e.weight = weight;
					e.countMin = countMin;
					e.countMax = countMax;
					entries.add(e);
					spawns.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) mob, weight, countMin, countMax));
					LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("  added spawn entry " + e.mob.getSimpleName() + ": " + weight + ", " + countMin + ", " + countMax + " at " + biome.getBiomeName());
				}
			}
		}
	}
	
	public static void reload() {
		for (int i = entries.size() - 1; i >= 0; i--) {
			SpawnEntry e = entries.get(i);
			List<Biome.SpawnListEntry> spawns = e.biome.getSpawnableList(e.type);
			for (Biome.SpawnListEntry entry: spawns) {
				if (entry.entityClass == e.mob) {
					if (e.add) {
						entry.itemWeight = 0;
						LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("  removed spawn entry " + e.mob.getSimpleName() + ": " + e.weight + ", " + e.countMin + ", " + e.countMax + " at " + e.biome.getBiomeName());
					} else {
						entry.itemWeight = e.weight;
						entry.minGroupCount = e.countMin;
						entry.maxGroupCount = e.countMax;
						LogManager.getLogger(NoMobSpawningOnTrees.MODID).info("  re-added spawn entry " + e.mob.getSimpleName() + ": " + e.weight + ", " + e.countMin + ", " + e.countMax + " at " + e.biome.getBiomeName());
					}
				}
			}
		}
		entries.clear();
	}
	
}
