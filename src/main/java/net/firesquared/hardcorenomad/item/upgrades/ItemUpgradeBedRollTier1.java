package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockBedRoll;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.block.BlockContainer;

public class ItemUpgradeBedRollTier1 extends ItemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockBedRoll.class;
	}

	@Override
	public int getTargetLevel()
	{
		return 0;
	}

	@Override
	public int getNewLevel()
	{
		return 1;
	}
	@Override
	public BlockContainer getContainerSingleton()
	{
		return (BlockContainer) Blocks.BLOCK_BEDROLL.getBlock();
	}
}
