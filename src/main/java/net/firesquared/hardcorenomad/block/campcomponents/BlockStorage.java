package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockStorage extends BlockCampComponent
{

	public BlockStorage()
	{
		super(Material.wood);
	}

	@Override
	protected boolean has3dRender()
	{
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityChest();//change to our version once it's implemented
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return createNewTileEntity(world, meta);
	}
}
