package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.world.World;

public enum EnumUpgradeTypes
{	
	CAMPFIRE((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 5),
	BEDROLL((BlockCampComponent)Blocks.BLOCK_BEDROLL.getBlock(), 5),
	CRAFTING((BlockCampComponent)Blocks.BLOCK_CRAFTING.getBlock(), 1),
	STORAGE((BlockCampComponent)Blocks.BLOCK_STORAGE.getBlock(), 1),
	ENCHANTING((BlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.getBlock(), 5),
	ANVIL((BlockCampComponent)Blocks.BLOCK_ANVIL.getBlock(), 1),
	COBBLEGEN((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 1),
	BREWING((BlockCampComponent)Blocks.BLOCK_BREWING.getBlock(), 1);
	
	EnumUpgradeTypes(BlockCampComponent container, int levels)
	{
		
	}
}
