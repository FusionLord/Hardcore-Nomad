package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeEnchantTableTier2 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.ENCHANTING;
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
