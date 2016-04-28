package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.render.tileentity.TESRWRMRL;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradable;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityUpgradableGeneric;
import net.fusionlord.hardcorenomad.common.tileentity.util.TileEntityUtils;
import net.fusionlord.hardcorenomad.common.init.ModCreativeTab;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
		TileEntityUpgradable tileEntity = createNewExtendedTileEntity(worldIn, meta);
		if (tileEntity == null)
		{
			tileEntity = new TileEntityUpgradableGeneric();
		}
		return tileEntity;
	}

	abstract TileEntityUpgradable createNewExtendedTileEntity(World worldIn, int meta);

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
	public int damageDropped(IBlockState state)
	{
		return state.getValue(LEVEL).ordinal();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		LogHelper.info(worldIn.getTileEntity(pos));
		TileEntityUpgradable tileEntity = TileEntityUtils.getTileEntity(worldIn, pos, TileEntityUpgradable.class);
		if (tileEntity!= null)
		{
			EnumFacing f = placer.getHorizontalFacing().getOpposite();
			EnumUpgrade l = EnumUpgrade.values()[stack.getMetadata()];
			tileEntity.setFacing(f);
			tileEntity.setUpgrade(l);
			tileEntity.markDirty();
			LogHelper.info("TileEntityInfo set!");
		}
	}

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
