package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityEnchantmentTable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockContainer
{
	public BlockEnchantmentTable()
	{
		super(Material.rock);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeMetal);
		setBlockTextureName(Reference.MOD_ID + ":" + getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		TileEntityEnchantmentTable tileEntityCampFire = new TileEntityEnchantmentTable();
		return tileEntityCampFire;
	}
}
