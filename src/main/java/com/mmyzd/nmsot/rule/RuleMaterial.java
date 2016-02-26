package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleMaterial extends Rule {

	Material material;
	
	public RuleMaterial(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getIdentifier(s, "material name");
		material = ObfuscationReflectionHelper.getPrivateValue(Material.class, null, name);
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block.getMaterial() == material;
	}

}
