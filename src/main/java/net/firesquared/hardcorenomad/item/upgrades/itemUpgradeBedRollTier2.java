package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockBedRoll;

public class itemUpgradeBedRollTier2 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockBedRoll.class;
	}

	@Override
	public int getTargetLevel()
	{
		return 1;
	}

	@Override
	public int getNewLevel()
	{
		return 2;
	}
}
