package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.block.BlockContainer;

public class ItemUpgradeEnchantTableTier5 extends ItemUpgrade
{
	@Override
	public Class getUpgradeTarget()
	{
		return BlockEnchantmentTable.class;
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
	
	@Override
	public BlockContainer getContainerSingleton()
	{
		return (BlockContainer) Blocks.BLOCK_ENCHANTMENTTABLE.getBlock();
	}
}
