package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnchantingTable extends BlockUpgradable
{
	public BlockEnchantingTable()
	{
		super(Material.wood, EnumUpgrade.DIAMOND);
		setRegistryName(ModInfo.ID, "enchantingtable");
		setUnlocalizedName("enchantingtable");
	}

	@Override
	BlockStateContainer createExtendedBlockState()
	{
		return null;
	}

	@Override
	TileEntityUpgradable createNewExtendedTileEntity(World worldIn, int meta)
	{
		return null;
	}

	@Override
	IBlockState getExtendedActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return null;
	}
}
