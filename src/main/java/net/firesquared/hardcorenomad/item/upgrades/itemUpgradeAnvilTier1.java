package net.firesquared.hardcorenomad.item.upgrades;

public class itemUpgradeAnvilTier1 extends itemUpgrade
{

	@Override
	public EnumUpgradeTypes getUpgradeTarget()
	{
		return EnumUpgradeTypes.ANVIL;
	}

	@Override
	public int getTargetLevel()
	{
		return 0;
	}

	@Override
	public int getNewLevel()
	{
		return 1;
	}

}
