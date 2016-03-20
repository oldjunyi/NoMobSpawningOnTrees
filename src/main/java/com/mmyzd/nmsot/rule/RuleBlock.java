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
		lhs = 0;
		rhs = OreDictionary.WILDCARD_VALUE - 1;
		if (RuleSet.getTokenEqualsIgnoreCase(s, ":")) {
			String damageStr = RuleSet.getToken(s);
			if (!damageStr.equals("*")) {
				String[] u = damageStr.split("-");
				if (u.length < 1 || u.length > 2) throw new Exception("Invalid block damage");
				for (int i = 0; i < u.length; i++)
					if (!StringUtils.isNumeric(u[i]) || u[i].length() > 18)
						throw new Exception("Invalid block damage");
				lhs = (int)Math.min(Long.parseLong(u[0]), rhs);
				if (u.length == 1) rhs = lhs;
				if (u.length == 2) rhs = (int)Math.min(Long.parseLong(u[1]), rhs);
				if (lhs > rhs) {
					int at = lhs;
					lhs = rhs;
					rhs = at;
				}
			}
		}
		block = GameRegistry.findBlock(modid, bname);
	}

	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block == block && entry.damage >= lhs && entry.damage <= rhs;
	}

}
