package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeEnchantTableTier5 extends itemUpgrade
{
	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.ENCHANTING;
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
