package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESREnchantingTable;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityEnchantingTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
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
	{ }

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
			double x = (double) pos.getX();
			double y = (double) pos.getY();
			double z = (double) pos.getZ();
			double particleX = x + 0.5D;
			double particleY = y + rand.nextDouble() * 6.0D / 16.0D + .7;
			double particleZ = z + 0.5D;
			double minOffset = -.5D;
			double maxOffset = rand.nextDouble() * 0.6D - 0.3D;

			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, particleX - minOffset, particleY, particleZ + maxOffset, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, particleX + minOffset, particleY, particleZ + maxOffset, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, particleX + maxOffset, particleY, particleZ - minOffset, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, particleX + maxOffset, particleY, particleZ + minOffset, 0.0D, 0.0D, 0.0D);
		}
	}
}
