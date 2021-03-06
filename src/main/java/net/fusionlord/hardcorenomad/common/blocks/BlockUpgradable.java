package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.fusionlord.hardcorenomad.common.tileentity.util.TileEntityUtils;
import net.fusionlord.hardcorenomad.common.init.ModCreativeTab;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BlockUpgradable extends BlockContainer
{
	public static PropertyEnum<EnumUpgrade> LEVEL = PropertyEnum.create("level", EnumUpgrade.class);
	public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	private List<EnumUpgrade> validLevels;

	BlockUpgradable(Material material, EnumUpgrade maxUpgrade)
	{
		this(material, maxUpgrade.ordinal());
	}

	private BlockUpgradable(Material materialIn, int maxLevel)
	{
		super(materialIn);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setBlockUnbreakable();
		validLevels = EnumUpgrade.getLevelsThrough(maxLevel);
	}



	public List<EnumUpgrade> getValidLevels()
	{
		return validLevels;
	}

	@Override
	public final BlockStateContainer createBlockState()
	{
		BlockStateContainer stateContainer = createExtendedBlockState();
		if (stateContainer != null && !stateContainer.getProperties().isEmpty()) {
			List<IProperty<?>> properties = (List<IProperty<?>>) stateContainer.getProperties();
			properties.add(0, LEVEL);
			properties.add(1, FACING);
			stateContainer = new BlockStateContainer(this, (IProperty<?>[]) properties.toArray());
		} else {
			stateContainer = new BlockStateContainer(this, LEVEL, FACING);
		}
		return stateContainer;
	}

	abstract BlockStateContainer createExtendedBlockState();

	@Override
	public final TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntityUpgradable tileEntity = null;
		try
		{
			tileEntity = (TileEntityUpgradable) getTileEntityClass().newInstance();
		}
		catch(InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return tileEntity;
	}

	@Override
	public final IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState blockState;
		TileEntityUpgradable tileEntityUpgradable = TileEntityUtils.getTileEntity(worldIn, pos, TileEntityUpgradable.class);
		if (tileEntityUpgradable == null)
			return super.getActualState(state, worldIn, pos);

		blockState = getExtendedActualState(state, worldIn, pos);
		if (blockState == null) blockState = super.getActualState(state, worldIn, pos);
		return blockState.withProperty(LEVEL, tileEntityUpgradable.getUpgrade()).withProperty(FACING, tileEntityUpgradable.getFacing());
	}

	abstract IBlockState getExtendedActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos);

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		//If it will harvest, delay deletion of the block until after getDrops
		return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> ret = getExtendedDrops(world, pos, state, fortune);
		return ret != null ? ret : super.getDrops(world, pos, state, fortune);
	}

	abstract ArrayList<ItemStack> getExtendedDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune);

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		// getDrops has been called, remove block;
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		LogHelper.debug(worldIn.getTileEntity(pos));
		TileEntityUpgradable tileEntity = TileEntityUtils.getTileEntity(worldIn, pos, TileEntityUpgradable.class);
		if (tileEntity!= null)
		{
			EnumFacing f = placer.getHorizontalFacing().getOpposite();
			EnumUpgrade l = EnumUpgrade.values()[stack.getMetadata()];
			tileEntity.setFacing(f);
			tileEntity.setUpgrade(l);
			onBlockPlacedExtended(worldIn, pos, state, placer, stack);
			tileEntity.markDirty();
			LogHelper.debug("TileEntityInfo set!");
		}
	}

	abstract void onBlockPlacedExtended(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack);

	@Override
	public Block setUnlocalizedName(String name)
	{
		return super.setUnlocalizedName(ModInfo.ID + "." + name);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.addAll(validLevels.stream().map(upgrade -> new ItemStack(this, 1, upgrade.ordinal())).collect(Collectors.toList()));
	}

	public abstract Class getTileEntityClass();

	public abstract TESRWRMRL getRender();
}
