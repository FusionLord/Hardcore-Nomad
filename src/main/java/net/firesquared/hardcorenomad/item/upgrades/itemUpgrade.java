package net.firesquared.hardcorenomad.item.upgrades;

import net.minecraft.item.Item;

public abstract class itemUpgrade extends Item
{
	public abstract EnumUpgradeTypes getUpgradeTarget();
	
	public abstract int getTargetLevel();
	
	public abstract int getNewLevel();
}
