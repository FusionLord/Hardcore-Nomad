package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeBedRollTier5 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.BEDROLL;
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
