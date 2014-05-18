package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;

public class itemUpgradeEnchantTableTier2 extends itemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockEnchantmentTable.class;
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
