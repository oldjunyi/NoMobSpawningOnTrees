package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

<<<<<<< HEAD
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.mmyzd.nmsot.IntegerRange;
=======
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
import com.mmyzd.nmsot.SpawningEntry;

public class RuleDimension extends Rule {

<<<<<<< HEAD
	private int index = -99999;
	
	public RuleDimension(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String token = RuleSet.getToken(s).toLowerCase();
		String indexString = IntegerRange.getIntegerStringFrom(token, 0);
		if (IntegerRange.validateIntegerLength(indexString) && token.equals(indexString)) {
			index = Integer.parseInt(indexString);
		} else {
			for (WorldServer world: DimensionManager.getWorlds()) {
				if (world.provider.getDimensionName().toLowerCase().replaceAll("\\s", "").equals(token)) {
					index = world.provider.getDimensionId();
					break;
				}
			}
		}
		
=======
	int index;
	
	public RuleDimension(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String tmp = RuleSet.getToken(s);
		try {
			index = Integer.parseInt(tmp);
		} catch (Exception e) {
			throw new Exception("Invalid dimension ID");
		}
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.provider.getDimensionId() == index;
	}

}
