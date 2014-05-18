package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockAnvil;

public class itemUpgradeAnvilTier1 extends itemUpgrade
{

	@Override
	public Class getUpgradeTarget()
	{
		return BlockAnvil.class;
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
