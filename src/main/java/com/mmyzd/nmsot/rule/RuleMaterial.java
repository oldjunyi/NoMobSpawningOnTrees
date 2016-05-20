package com.mmyzd.nmsot.rule;

import java.util.LinkedList;

import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.mmyzd.nmsot.SpawningEntry;

public class RuleMaterial extends Rule {

<<<<<<< HEAD
	private Material material = null;
	
	public RuleMaterial(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getToken(s);
=======
	Material material;
	
	public RuleMaterial(LinkedList<Character> s) throws Exception {
		RuleSet.nextPart(s);
		String name = RuleSet.getIdentifier(s, "material name");
>>>>>>> fadfc75ef6f4b7654eb38d208a04d4320c9c61e3
		material = ObfuscationReflectionHelper.getPrivateValue(Material.class, null, name);
	}
	
	@Override
	public boolean apply(SpawningEntry entry) {
		return entry.block.getMaterial() == material;
	}

}
