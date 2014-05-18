package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockBedRoll;

public class itemUpgradeBedRollTier5 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockBedRoll.class;
	}

	@Override
	public int getTargetLevel()
	{
		return 4;
	}

	@Override
	public int getNewLevel()
	{
		return 5;
	}
}
