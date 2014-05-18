package net.firesquared.hardcorenomad.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStorage extends BlockContainer implements IBlockCampComponent
{

	public BlockStorage()
	{
		super(Material.wood);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		// TODO: Make a tile entity for the crafting table :/
		return null;
	}

	@Override
	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		return new ItemStack(Blocks.BLOCK_STORAGE.getBlock());
	}
}
