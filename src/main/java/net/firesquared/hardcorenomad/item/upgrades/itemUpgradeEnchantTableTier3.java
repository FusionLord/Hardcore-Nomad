package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;

public class itemUpgradeEnchantTableTier3 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockEnchantmentTable.class;
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
