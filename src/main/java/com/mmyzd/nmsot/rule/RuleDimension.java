package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleDimension extends Rule {

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
					index = world.provider.dimensionId;
					break;
				}
			}
		}
		
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.world.provider.dimensionId == index;
	}

}
