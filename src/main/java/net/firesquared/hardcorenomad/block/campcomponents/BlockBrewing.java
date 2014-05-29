package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.GUIHandler;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
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
	public UpgradeType getType()
	{
		return UpgradeType.BREWING_STAND;
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX, float hitY, float hitZ, float unknown)
	{
        if (world.isRemote)
            return true;
        TileEntityBrewingStand teBrewingStand = (TileEntityBrewingStand)world.getTileEntity(x, y, z);

        if (teBrewingStand != null)
        	player.openGui(HardcoreNomad.instance, GUIHandler.GUIType.BREWING_STAND_BLOCK.ID, world, hitX, y, z);

        return true;
	}
}
