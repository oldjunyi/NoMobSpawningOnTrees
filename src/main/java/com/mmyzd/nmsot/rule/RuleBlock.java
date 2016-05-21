package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.mmyzd.nmsot.IntegerRange;
import com.mmyzd.nmsot.SpawningEntry;

public class RuleBlock extends Rule {
	
	private Block block = Blocks.air;
	private int lhs = 0, rhs = OreDictionary.WILDCARD_VALUE - 1;
	
	public RuleBlock(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String modid = RuleSet.getToken(s);
		RuleSet.nextPart(s);
		String bname = RuleSet.getToken(s);
		if (RuleSet.getTokenEqualsIgnoreCase(s, ":")) {
			IntegerRange range = IntegerRange.parse(RuleSet.getToken(s));
			lhs = range.lhs;
			rhs = range.rhs;
		}
		
		block = Block.blockRegistry.getObject(new ResourceLocation(modid, bname));
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block == block && entry.damage >= lhs && entry.damage <= rhs;
	}

}
