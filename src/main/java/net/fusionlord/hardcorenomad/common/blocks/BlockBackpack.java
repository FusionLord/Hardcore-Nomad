package net.fusionlord.hardcorenomad.common.blocks;

import com.google.common.collect.Lists;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityBackpack;
import net.fusionlord.hardcorenomad.common.tileentity.util.TileEntityUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockBackpack extends BlockUpgradable
{
	public BlockBackpack()
	{
		super(Material.cloth, EnumUpgrade.GOLD);
		super.setHardness(0.8F);
		setRegistryName("backpack");
		setUnlocalizedName("backpack");
	}

	@Override
	BlockStateContainer createExtendedBlockState()
	{
		return null;
	}

	@Override
	IBlockState getExtendedActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return null;
	}

	@Override
	ArrayList<ItemStack> getExtendedDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		TileEntityBackpack backpack = TileEntityUtils.getTileEntity(world, pos, TileEntityBackpack.class);
		if (backpack == null) return null;
		ArrayList<ItemStack> ret = Lists.newArrayList();
		ItemStack stack = new ItemStack(this, 1, backpack.getUpgrade().ordinal());
		NBTTagCompound tag = new NBTTagCompound();
		backpack.writeExtraData(tag);
		if (!tag.hasNoTags())
			stack.setTagCompound(tag);
		ret.add(stack);
		return ret;
	}

	@Override
	void onBlockPlacedExtended(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntityBackpack backpack = TileEntityUtils.getTileEntity(world, pos, TileEntityBackpack.class);
		if (backpack == null || stack.getTagCompound() == null) return;
		backpack.readExtraData(stack.getTagCompound());
	}

	@Override
	public Class getTileEntityClass()
	{
		return TileEntityBackpack.class;
	}

	@Override
	public TESRWRMRL getRender()
	{
		return null;
	}
}
