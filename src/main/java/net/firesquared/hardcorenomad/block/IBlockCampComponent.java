package net.firesquared.hardcorenomad.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;



public interface IBlockCampComponent
{
	public ItemStack packIntoItemStack(World world, int x, int y, int z);
}
