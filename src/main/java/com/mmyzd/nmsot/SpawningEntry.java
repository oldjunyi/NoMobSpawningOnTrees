package com.mmyzd.nmsot;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
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
		x = (int) Math.floor(event.x);
		y = (int) event.y - 1;
		z = (int) Math.floor(event.z);
		pos = new BlockPos(x, y, z);
		world = event.world;
		blockState = world.getBlockState(pos);
		block = blockState.getBlock();
		damage = block.damageDropped(blockState);
		entity = event.entity;
	}

}
