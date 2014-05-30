package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityAnvil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnvil extends BlockCampComponent
{

	public BlockAnvil()
	{
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAnvil();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX, float hitY, float hitZ, float unknown)
	{
		if(world.isRemote)
			return true;
		player.displayGUIAnvil(x, y, z);
		return true;
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
}
