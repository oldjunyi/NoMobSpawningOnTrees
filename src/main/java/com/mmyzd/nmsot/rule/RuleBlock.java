package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.mmyzd.nmsot.SpawningEntry;


public class RuleBlock extends Rule {
	
	Block block;
	int lhs, rhs;
	
	public RuleBlock(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String modid = RuleSet.getIdentifier(s, "modid");
		RuleSet.nextPart(s);
		String bname = RuleSet.getIdentifier(s, "block name");
		int any = OreDictionary.WILDCARD_VALUE;
		lhs = 0;
		rhs = any - 1;
		if (RuleSet.getToken(s, ":")) {
			String damageStr = RuleSet.getToken(s);
			if (!damageStr.equals("*")) {
				String[] ids = damageStr.split("-");
				for (int i = 0; i < ids.length; i++)
					if (!StringUtils.isNumeric(ids[i]) || ids[i].length() > 18)
						throw new Exception("Invalid data value");
				if (ids.length < 1 || ids.length > 2) throw new Exception("Invalid data value");
				lhs = (int)Math.min(Long.parseLong(ids[0]), (long)any);
				if (ids.length == 1) rhs = lhs;
				if (ids.length == 2) rhs = (int)Math.min(Long.parseLong(ids[1]), (long)any - 1);
			}
		}
		block = GameRegistry.findBlock(modid, bname);
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block == block && entry.damage >= lhs && entry.damage <= rhs;
	}

}
