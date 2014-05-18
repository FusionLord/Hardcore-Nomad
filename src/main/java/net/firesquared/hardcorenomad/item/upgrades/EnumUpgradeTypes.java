package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.IBlockCampComponent;
import net.firesquared.hardcorenomad.block.Blocks;

public enum EnumUpgradeTypes
{	
	CAMPFIRE((IBlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 5),
	BEDROLL((IBlockCampComponent)Blocks.BLOCK_BEDROLL.getBlock(), 5),
	CRAFTING((IBlockCampComponent)Blocks.BLOCK_CRAFTING.getBlock(), 1),
	STORAGE((IBlockCampComponent)Blocks.BLOCK_STORAGE.getBlock(), 1),
	ENCHANTING((IBlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.getBlock(), 5),
	ANVIL((IBlockCampComponent)Blocks.BLOCK_ANVIL.getBlock(), 1),
	COBBLEGEN((IBlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 1),
	BREWING((IBlockCampComponent)Blocks.BLOCK_BREWING.getBlock(), 1);
	
	EnumUpgradeTypes(IBlockCampComponent container, int levels)
	{
		
	}
}
