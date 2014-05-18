package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;

public class itemUpgradeEnchantTableTier4 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockEnchantmentTable.class;
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
}
