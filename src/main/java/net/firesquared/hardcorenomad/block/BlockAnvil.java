package net.firesquared.hardcorenomad.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnvil extends BlockContainer implements IBlockCampComponent
{

	public BlockAnvil()
	{
		super(Material.iron);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return null;
	}

	@Override public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		return null;
	}
}
