package net.firesquared.hardcorenomad.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBedRoll extends IBlockCampComponent
{

	public BlockBedRoll()
	{
		super(Material.cloth);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		// TODO: Make a tile entity for the crafting table :/
		return null;
	}
}
