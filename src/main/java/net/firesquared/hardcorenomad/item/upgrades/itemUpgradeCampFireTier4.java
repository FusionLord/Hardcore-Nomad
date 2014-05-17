package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeCampFireTier4 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.CAMPFIRE;
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
