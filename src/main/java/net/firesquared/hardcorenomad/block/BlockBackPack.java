package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class BlockBackPack extends BlockContainer
{

	public BlockBackPack()
	{
		super(Material.cloth);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeCloth);
		//setBlockTextureName(Helper.Strings.MOD_ID + ":" + getUnlocalizedName());
		setBlockBounds(0.1f, 0f, 0.3f, .9f, .9f, .7f);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
 		TileEntityBackPack backpack = (TileEntityBackPack)world.getTileEntity(x, y, z);
		ItemStack itemStack = new ItemStack(Items.ITEM_BACKPACK.item, 1);
		itemStack.stackTagCompound = new NBTTagCompound();
		backpack.writeExtraNBT(itemStack.stackTagCompound);
		drops.add(itemStack);
		return drops;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int metadata)
	{
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityBackPack(metadata);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
		// Don't return anything, because item is dropped when it is broken.
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6,
			float par7, float par8, float par9)
	{
		TileEntityBackPack te = Tiles.<TileEntityBackPack>getTileEntity(world, x, y, z);
		if(te == null)
			return true;
		player.openGui(HardcoreNomad.instance, GUIType.BACKPACK_TILEENTITY.ID, world, x, y, z);
		return true;
	}
	
	

	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean getCanBlockGrass()
	{
		return false;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
}
