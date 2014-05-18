package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCampFire;

public class itemUpgradeCampFireTier3 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockCampFire.class;
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
