package com.mmyzd.nmsot;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class SpawningEntry {

	public BlockPos pos; // pos and x, y, z are the position of spawn block
	public int x, y, z, damage;
	public IBlockState blockState;
	public World world;
	public Block block;
	public Entity entity;

	public void init(LivingSpawnEvent.CheckSpawn event) {
		x = (int) Math.floor(event.getX());
		y = (int) event.getY() - 1;
		z = (int) Math.floor(event.getZ());
		pos = new BlockPos(x, y, z);
		world = event.getWorld();
		blockState = world.getBlockState(pos);
		block = blockState.getBlock();
		damage = block.damageDropped(blockState);
		entity = event.getEntity();
	}

}
