package net.firesquared.hardcorenomad.block;

import java.util.Random;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.CampFireTypes;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCampFire extends BlockContainer implements IBlockCampComponent
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
	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_CAMPFIRE.getBlock());

		TileEntityCampFire tileEntityCampFire = TileEntityHelper.getTileEntity(world, x, y, z, TileEntityCampFire.class);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		tileEntityCampFire.writeToNBT(nbtTagCompound);

		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return false;
	}
	
	@Override
	public void updateTick(World w, int x, int y, int z, Random rand)
	{
		for(int j = 0; j < 6; j++)
		{
			w.spawnParticle("flame", x + w.rand.nextFloat() - .5f, y + .25f, z + w.rand.nextFloat() - .5f,
					w.rand.nextFloat() - .5f,  w.rand.nextFloat() / 2, w.rand.nextFloat() - .5f);
		}
	}
}
