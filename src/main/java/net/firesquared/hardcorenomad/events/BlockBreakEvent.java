package net.firesquared.hardcorenomad.events;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBreakEvent
{
	@SubscribeEvent
	public void onBreakEvent(BlockEvent.BreakEvent event) {
		if (event.block == Blocks.cobblestone || event.block == Blocks.stone)
		{
			event.block.harvestBlock(event.world, event.getPlayer(), event.x, event.y, event.z, 1);
		}
	}

	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
		if (event.block == Blocks.cobblestone || event.block == Blocks.stone)
		{
			if (!event.isSilkTouching)
			{
				int rand = event.world.rand.nextInt(6);
				event.drops.clear();
				event.drops.add(new ItemStack(Items.ITEM_MISC_PEBBLE.getItem(), rand + 1));
				event.dropChance = 1.0F;
			}
		}
	}
}
