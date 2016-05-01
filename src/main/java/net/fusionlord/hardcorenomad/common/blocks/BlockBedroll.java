package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityBedroll;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class BlockBedroll extends BlockUpgradable
{
	public BlockBedroll()
	{
		super(Material.cloth, EnumUpgrade.DIAMOND);
		setRegistryName(ModInfo.ID, "bedroll");
		setUnlocalizedName("bedroll");
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
		return null;
	}

	@Override
	void onBlockPlacedExtended(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{

	}

	@Override
	public Class getTileEntityClass()
	{
		return TileEntityBedroll.class;
	}

	@Override
	public TESRWRMRL getRender()
	{
		return null;
	}
}
