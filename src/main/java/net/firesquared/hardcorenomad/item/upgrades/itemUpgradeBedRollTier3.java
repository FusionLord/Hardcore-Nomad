package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockBedRoll;

public class itemUpgradeBedRollTier3 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockBedRoll.class;
	}

	@Override
	public int getTargetLevel()
	{
		return 2;
	}

	@Override
	public int getNewLevel()
	{
		return 3;
	}
}
