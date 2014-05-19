package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCampFire;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.block.BlockContainer;

public class ItemUpgradeCampFireTier4 extends ItemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockCampFire.class;
	}

	@Override
	public int getTargetLevel()
	{
		return 3;
	}

	@Override
	public int getNewLevel()
	{
		return 4;
	}

	@Override
	public BlockContainer getContainerSingleton()
	{
		return (BlockContainer) Blocks.BLOCK_CAMPFIRE.getBlock();
	}
}
