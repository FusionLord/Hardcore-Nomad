package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCobbleGenerator;

public class itemUpgradeCobbleGenTier1 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockCobbleGenerator.class;
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
