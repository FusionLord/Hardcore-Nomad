package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int meta, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			entityPlayer.openGui(HardcoreNomad.instance, 5, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAnvil(getType());
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}

	@Override
	public UpgradeType getType()
	{
		return UpgradeType.ANVIL;
	}
}
