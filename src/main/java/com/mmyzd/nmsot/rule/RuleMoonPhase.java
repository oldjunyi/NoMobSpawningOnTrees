package com.mmyzd.nmsot.rule;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

import net.minecraft.world.biome.Biome;

public class RuleMoonPhase extends Rule {

	private static final String[] PHASES = { "fullmoon", "waninggibbous", "lastquarter", "waningcrescent", "newmoon",
			"waxingcrescent", "firstquarter", "waxinggibbous" };

	private int lhs = IntegerRange.INVALID_INT, rhs = IntegerRange.INVALID_INT;

	public RuleMoonPhase(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		try {
			IntegerRange range = new IntegerRange(s);
			lhs = range.lhs;
			rhs = range.rhs;
		} catch (Exception e) {
			String token = RuleSet.getToken(s).toLowerCase();
			for (int i = 0; i < PHASES.length; i++) {
				if (PHASES[i].equals(token)) {
					lhs = i;
					rhs = i;
					break;
				}
			}
			if (lhs == IntegerRange.INVALID_INT) {
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such moon phase range or name: " + token);
			}
		}
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		int phase = entry.world.provider.getMoonPhase(entry.world.getWorldTime());
		return lhs <= phase && phase <= rhs;
	}

}
