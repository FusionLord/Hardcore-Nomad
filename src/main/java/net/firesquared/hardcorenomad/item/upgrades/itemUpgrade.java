package net.firesquared.hardcorenomad.item.upgrades;

import net.minecraft.block.BlockContainer;
import net.minecraft.item.Item;

public abstract class itemUpgrade extends Item
{
	public abstract Class getUpgradeTarget();
	
	public abstract BlockContainer getContainerSingleton();
	
	public abstract int getTargetLevel();
	
	public abstract int getNewLevel();
}
