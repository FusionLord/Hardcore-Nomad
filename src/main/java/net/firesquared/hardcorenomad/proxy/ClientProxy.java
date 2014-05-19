package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.client.render.*;
import net.firesquared.hardcorenomad.client.render.RenderBackPackItem;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.TileEntityEnchantmentTable;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerItems()
	{
		ModelRegistry.initialise();

		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKBASIC.getItem(), new RenderBackPackItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKIMPROVED.getItem(), new RenderBackPackItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKADVANCED.getItem(), new RenderBackPackItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKARMORED.getItem(), new RenderBackPackItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.getItem(), new RenderRocksItem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()), new RenderEnchantingItem());

		// ##UPGRADES##
		//RenderUpgradeItem renderUpgradeItem = new RenderUpgradeItem();
		// Enchanting Table
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTTABLE_TIER1.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTTABLE_TIER2.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTTABLE_TIER3.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTTABLE_TIER4.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTTABLE_TIER5.getItem(), new RenderUpgradeItem());
		// Campfire
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CAMPFIRE_TIER1.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CAMPFIRE_TIER2.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CAMPFIRE_TIER3.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CAMPFIRE_TIER4.getItem(), new RenderUpgradeItem());
		// Bedroll
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL_TIER1.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL_TIER2.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL_TIER3.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL_TIER4.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL_TIER5.getItem(), new RenderUpgradeItem());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampFire.class, new RenderCampfireTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBedRoll.class, new RenderBedRollTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());
		
		super.registerItems();
	}

}
