package net.fusionlord.hardcorenomad.common.blocks;

import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockUpgradeBase extends BlockContainer
{
	public static PropertyEnum<EnumUpgrade> LEVEL = PropertyEnum.create("level", EnumUpgrade.class);
	private EnumUpgrade[] validLevels;

	protected BlockUpgradeBase(Material material, EnumUpgrade upgrade)
	{
		this(material, upgrade.ordinal());
	}

	protected BlockUpgradeBase(Material materialIn, int maxLevel)
	{
		super(materialIn);
		validLevels = EnumUpgrade.getLevelsThrough(maxLevel);
	}

	public EnumUpgrade[] getValidLevels()
	{
		return validLevels;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		BlockStateContainer stateContainer = createExtendedBlockState();
		if (stateContainer != null && !stateContainer.getProperties().isEmpty()) {
			List<IProperty<?>> properties = new ArrayList<IProperty<?>>();
			properties.add(LEVEL);
			properties.addAll(stateContainer.getProperties());
			stateContainer = new BlockStateContainer(this, (IProperty<?>[]) properties.toArray());
		} else {
			stateContainer = new BlockStateContainer(this, LEVEL);
		}
		return stateContainer;
	}

	abstract BlockStateContainer createExtendedBlockState();
}
