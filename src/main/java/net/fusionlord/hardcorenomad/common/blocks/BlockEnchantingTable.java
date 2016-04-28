package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESREnchantingTable;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityEnchantingTable;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

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

	@Override
	public Class getTileEntityClass()
	{
		return TileEntityEnchantingTable.class;
	}

	@Override
	public TESRWRMRL getRender()
	{
		return new TESREnchantingTable();
	}


	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		IBlockState blockState = state.getActualState(world, pos);
		if(blockState.getValue(LEVEL).ordinal() >= EnumUpgrade.IRON.ordinal())
		{
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D + .75;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			if(rand.nextDouble() < 0.1D)
			{
				world.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.block_furnace_fire_crackle, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
//			world.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
//			world.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
//			world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
//			world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
		}
	}
}
