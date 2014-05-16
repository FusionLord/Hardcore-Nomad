package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.ItemBackPackAdvanced;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockBackPack extends BlockContainer
{
	//TODO: Figure out block icons for the backpack or if we are going to do something else

	public static <T> T getTileEntity(IBlockAccess access, int x, int y, int z, Class<T> clazz)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		return !clazz.isInstance(te) ? null : (T) te;
	}

	public BlockBackPack()
	{
		super(Material.cloth);
		setHardness(1.0F);
		setResistance(10.0F);
		setStepSound(soundTypeCloth);
		//setTileEntity(TileEntityBackPack.class);
		setBlockTextureName(Reference.MOD_ID + ":" + getUnlocalizedName());
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
	{
		TileEntityBackPack tileEntityBackPack = getTileEntity(world, x, y, z, TileEntityBackPack.class);

		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		tileEntityBackPack.writeToNBT(nbtTagCompound);

		ItemStack itemStack = null;
		switch(BackPackTypes.values()[nbtTagCompound.getInteger("backPackType")])
		{
			case BACKPACK_BASIC:
				itemStack = new ItemStack(Items.ITEM_BACKPACKBASIC.getItem(), 1);
				break;
			case BACKPACK_IMPROVED:
				itemStack = new ItemStack(Items.ITEM_BACKPACKIMPROVED.getItem(), 1);
				break;
			case BACKPACK_ARMORED:
				itemStack = new ItemStack(Items.ITEM_BACKPACKARMORED.getItem(), 1);
				break;
			case BACKPACK_ADVANCED:
				itemStack = new ItemStack(Items.ITEM_BACKPACKADVANCED.getItem(), 1);
				break;
		}

		// Remove X,Y,Z or it will render there when placed...
		nbtTagCompound.removeTag("x");
		nbtTagCompound.removeTag("y");
		nbtTagCompound.removeTag("z");

		// Save NBT Data to itemStack
		itemStack.setTagCompound(nbtTagCompound);

		// Drop item into world like magic :D
		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		TileEntityBackPack tileEntityBackPack = new TileEntityBackPack();
		tileEntityBackPack.setBlockMeta(var2);
		return tileEntityBackPack;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
		// Don't return anything, because item is dropped when it is broken.
	}

	@Override
	public int getRenderType()
	{
		return TileEntityBackPack.ModelID;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6,
			float par7, float par8, float par9)
	{
		player.openGui(HardcoreNomad.instance, 0, world, x, y, z);
		return true;
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
	public boolean getCanBlockGrass()
	{
		return false;
	}
}
