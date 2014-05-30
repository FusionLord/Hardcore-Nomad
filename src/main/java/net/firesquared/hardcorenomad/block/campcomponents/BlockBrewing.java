package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBrewingStand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockBrewing extends BlockCampComponent
{

	public BlockBrewing()
	{
		super(Material.rock);
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX, float hitY, float hitZ, float unknown)
	{
        TileEntityBrewingStand teBrewingStand = (TileEntityBrewingStand)world.getTileEntity(x, y, z);
        if (teBrewingStand != null)
        	return super.onBlockActivated(world, x, y, z, player, hitX, hitY, hitZ, unknown);
        return false;
	}
}
