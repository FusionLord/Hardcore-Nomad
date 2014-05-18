package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockBrewing;

public class itemUpgradeBrewingStandTier1 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockBrewing.class;
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
}
