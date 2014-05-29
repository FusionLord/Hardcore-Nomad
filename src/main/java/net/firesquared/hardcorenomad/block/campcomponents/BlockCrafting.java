package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCrafting extends BlockCampComponent
{
	public BlockCrafting()
	{
		super(Material.wood);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int meta, float hitX, float hitY, float hitZ)
	{
		if (entityPlayer.isSneaking())
		{
			System.out.println(meta);
			return true;
		}
		if (!world.isRemote)
		{
			entityPlayer.openGui(HardcoreNomad.instance, GUIType.CRAFTINGTABLE_BLOCK.ID, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCrafting();
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}

	@Override
	public UpgradeType getType()
	{
		return UpgradeType.CRAFTING_TABLE;
	}
}
