package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

<<<<<<< HEAD
import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.NoMobSpawningOnTrees;
=======
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
import com.mmyzd.nmsot.SpawningEntry;

public class RuleChance extends Rule {
	
<<<<<<< HEAD
	private double prob = 0.0;
=======
	double prob;
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	
	public RuleChance(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String tmp = RuleSet.getToken(s);
		try {
			prob = Double.parseDouble(tmp);
		} catch (Exception e) {
			throw new Exception("Invalid probability");
		}
<<<<<<< HEAD
		if (prob < 0 || prob > 1) {
			LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("Probability should fit in 0.0 ~ 1.0");
		}
=======
		if (prob < 0 || prob > 1) throw new Exception("Probability should fit in 0.0 ~ 1.0");
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.rand.nextDouble() < prob;
	}

}
