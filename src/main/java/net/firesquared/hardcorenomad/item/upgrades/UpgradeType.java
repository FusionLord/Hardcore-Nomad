package net.firesquared.hardcorenomad.item.upgrades;

public enum UpgradeType
{
	ANVIL(1),
	BEDROLL(4),
	BREWINGSTAND(1),
	CAMPFIRE(5),
	COBBLEGEN(1),
	CRAFTINGTABLE(1),
	ENCHANTINGTABLE(5),
	;
	private final int levelCount;

	UpgradeType(int levelCount)
	{
		this.levelCount = levelCount;
	}

	public int getLevelCount()
	{
		return levelCount;
	}
}
