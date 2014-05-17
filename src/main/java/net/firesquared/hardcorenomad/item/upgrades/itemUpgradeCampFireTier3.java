package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeCampFireTier3 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.CAMPFIRE;
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
