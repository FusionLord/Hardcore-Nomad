package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class BlockCampFire extends BlockContainer
{
	public BlockCampFire()
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
		TileEntityCampFire tileEntityCampFire = new TileEntityCampFire();
		//tileEntityCampFire.setBlockMeta(var2);
		return tileEntityCampFire;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		//TileEntityCampFire tileEntityCampFire = (TileEntityCampFire)world.getTileEntity(x, y, z);

		//if (tileEntityCampFire != null)
		//{
			player.openGui(HardcoreNomad.instance, 3, world, x, y, z);
		//}

		return true;
	}
}
