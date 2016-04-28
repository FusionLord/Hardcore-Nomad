package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityAnvil;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAnvil extends BlockUpgradable
{
	public BlockAnvil()
	{
		super(Material.anvil, EnumUpgrade.WOOD);
		setRegistryName("anvil");
		setUnlocalizedName("anvil");
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
	public Class getTileEntityClass()
	{
		return TileEntityAnvil.class;
	}

	@Override
	public TESRWRMRL getRender()
	{
		return null;
	}
}
