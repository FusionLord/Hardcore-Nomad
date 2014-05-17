package net.firesquared.hardcorenomad.item.upgrades;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.world.World;

public enum EnumUpgradeTypes
{	
	CAMPFIRE((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock()),
	BEDROLL((BlockCampComponent)Blocks.BLOCK_BEDROLL.getBlock()),
	CRAFTING((BlockCampComponent)Blocks.BLOCK_CRAFTING.getBlock()),
	STORAGE((BlockCampComponent)Blocks.BLOCK_STORAGE.getBlock()),
	ENCHANTING((BlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()),
	ANVIL((BlockCampComponent)Blocks.BLOCK_ANVIL.getBlock()),
	COBBLEGEN((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock()),
	BREWING((BlockCampComponent)Blocks.BLOCK_BREWING.getBlock());
	
	EnumUpgradeTypes(BlockCampComponent container)
	{
		
	}
}
