package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityEnchantmentTable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockCampComponent
{
	public BlockEnchantmentTable()
	{
		super(Material.rock);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityEnchantmentTable();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int meta, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			entityPlayer.openGui(HardcoreNomad.instance, GUIType.ENCHANTMENT_BLOCK.ID, world, x, y, z);
		}
		return true;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int meta)
	{
		return true;
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
}
