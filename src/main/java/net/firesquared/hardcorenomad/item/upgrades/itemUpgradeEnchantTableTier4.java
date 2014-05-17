package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeEnchantTableTier4 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.ENCHANTING;
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
