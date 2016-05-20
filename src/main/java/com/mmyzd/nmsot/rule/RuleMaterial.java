package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.material.Material;

import com.mmyzd.nmsot.SpawningEntry;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class RuleMaterial extends Rule {

	private Material material = null;
	
	public RuleMaterial(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getToken(s);
		material = ObfuscationReflectionHelper.getPrivateValue(Material.class, null, name);
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block.getMaterial() == material;
	}

}
