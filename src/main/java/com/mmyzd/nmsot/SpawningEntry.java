package com.mmyzd.nmsot;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class SpawningEntry {

	public int x, y, z, damage; // x, y, z are the position of spawn block
	public World world;
	public Block block;
	public Entity entity;

	public void init(LivingSpawnEvent.CheckSpawn event) {
		x = (int) Math.floor(event.x);
		y = (int) event.y - 1;
		z = (int) Math.floor(event.z);
		world = event.world;
		block = world.getBlock(x, y, z);
		damage = block.getDamageValue(world, x, y, z);
		entity = event.entity;
	}

}
