package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.CampFireTypes;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class BlockCampFire extends BlockCampComponent
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
			player.openGui(HardcoreNomad.instance, 2, world, x, y, z);
		//}

		return true;
	}

	public CampFireTypes getCampFireType(World world, int x, int y, int z) {
		TileEntityCampFire tileEntityCampFire = TileEntityHelper.getTileEntity(world, x, y, z, TileEntityCampFire.class);
		return tileEntityCampFire.getCampFireType();
	}

	@Override
	public void Deploy(World world, int x, int y, int z, int side, int hitX, int hitY, int hitZ, ItemStack stack, EntityPlayer player, int upgradeLevel, TileEntityBackPack backPack) {
		if (this.canPlaceBlockAt(world, x, y, z)) {
			int i1 = this.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);

			if(world.setBlock(x, y, z, this, i1, 3))
			{
				if(world.getBlock(x, y, z) == this)
				{
					this.onBlockPlacedBy(world, x, y, z, player, stack);
					this.onPostBlockPlaced(world, x, y, z, i1);

					TileEntityCampFire tileEntityCampFire = TileEntityHelper.getTileEntity(world, x, y, z, TileEntityCampFire.class);
					tileEntityCampFire.setCampFireType(CampFireTypes.values()[upgradeLevel]);
					tileEntityCampFire.setTileEntityBackPack(backPack);
				}
			}
		}
	}
}
