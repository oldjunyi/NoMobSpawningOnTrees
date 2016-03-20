package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;

import com.mmyzd.nmsot.SpawningEntry;

import cpw.mods.fml.common.registry.GameRegistry;

public class RuleBlock extends Rule {
	
	Block block;
	int lhs, rhs;
	
	public RuleBlock(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String modid = RuleSet.getIdentifier(s, "modid");
		RuleSet.nextPart(s);
		String bname = RuleSet.getIdentifier(s, "block name");
		lhs = 0;
		rhs = OreDictionary.WILDCARD_VALUE - 1;
		if (RuleSet.getTokenEqualsIgnoreCase(s, ":")) {
			IntegerRange range = IntegerRange.parse(RuleSet.getToken(s));
			lhs = range.lhs;
			rhs = range.rhs;
		}
		block = GameRegistry.findBlock(modid, bname);
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block == block && entry.damage >= lhs && entry.damage <= rhs;
	}

}
