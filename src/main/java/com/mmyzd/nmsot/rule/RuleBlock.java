package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.Block;
<<<<<<< HEAD
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.mmyzd.nmsot.IntegerRange;
=======
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
import com.mmyzd.nmsot.SpawningEntry;

public class RuleBlock extends Rule {
	
<<<<<<< HEAD
	private Block block = Blocks.air;
	private int lhs = 0, rhs = OreDictionary.WILDCARD_VALUE - 1;
	
	public RuleBlock(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String modid = RuleSet.getToken(s);
		RuleSet.nextPart(s);
		String bname = RuleSet.getToken(s);
=======
	Block block;
	int lhs, rhs;
	
	public RuleBlock(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String modid = RuleSet.getIdentifier(s, "modid");
		RuleSet.nextPart(s);
		String bname = RuleSet.getIdentifier(s, "block name");
		lhs = 0;
		rhs = OreDictionary.WILDCARD_VALUE - 1;
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
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
