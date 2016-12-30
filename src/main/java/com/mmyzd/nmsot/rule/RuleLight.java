package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.NoMobSpawningOnTrees;
import com.mmyzd.nmsot.SpawningEntry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public class RuleLight extends Rule {

	private int type = 0, lhs, rhs;

	public RuleLight(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		IntegerRange range = new IntegerRange(s);
		lhs = range.lhs;
		rhs = range.rhs;
		if (RuleSet.getTokenEqualsIgnoreCase(s, ",")) {
			String token = RuleSet.getToken(s).toLowerCase();
			if (token.equals("sky")) {
				type = 1;
			} else if (token.equals("block")) {
				type = 2;
			} else if (token.equals("mixed")) {
				type = 0;
			} else {
				LogManager.getLogger(NoMobSpawningOnTrees.MODID).warn("No such light type: " + token);
			}
		}
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		BlockPos footPos = entry.pos.up();
		if (type == 0) {
			int skyLightSub = entry.world.calculateSkylightSubtracted(1.0f);
			int skyLight = entry.world.getLightFor(EnumSkyBlock.SKY, footPos) - skyLightSub;
			int blockLight = entry.world.getLightFor(EnumSkyBlock.BLOCK, footPos);
			int mixedLight = Math.max(blockLight, skyLight);
			return lhs <= mixedLight && mixedLight <= rhs;
		} else if (type == 1) {
			int skyLightSub = entry.world.calculateSkylightSubtracted(1.0f);
			int skyLight = entry.world.getLightFor(EnumSkyBlock.SKY, footPos) - skyLightSub;
			return lhs <= skyLight && skyLight <= rhs;
		} else if (type == 2) {
			int blockLight = entry.world.getLightFor(EnumSkyBlock.BLOCK, footPos);
			return lhs <= blockLight && blockLight <= rhs;
		}
		return false;
	}

}
