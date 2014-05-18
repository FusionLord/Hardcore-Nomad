package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCrafting;

public class itemUpgradeCraftingTableTier1 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockCrafting.class;
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
