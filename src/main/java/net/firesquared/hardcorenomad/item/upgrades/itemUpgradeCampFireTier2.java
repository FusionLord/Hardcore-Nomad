package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeCampFireTier2 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.CAMPFIRE;
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
