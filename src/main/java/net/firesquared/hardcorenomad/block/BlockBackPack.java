package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBackPack extends BlockCampComponent
{
	public BlockBackPack()
	{
		super(Material.cloth);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeCloth);
		setBlockBounds(0.1f, 0f, 0.3f, .9f, .9f, .7f);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityBackPack backpack = (TileEntityBackPack)world.getTileEntity(x, y, z);
		ItemStack itemStack = new ItemStack(this, 1);
		NBTTagCompound itemTag = itemStack.getTagCompound();

		backpack.writeExtraNBT(itemTag);

		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityBackPack();
	}

	@Override
	public Item getItemDropped(int id, Random random, int meta)
	{
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
	{
		player.openGui(HardcoreNomad.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public boolean getCanBlockGrass()
	{
		return false;
	}

}
