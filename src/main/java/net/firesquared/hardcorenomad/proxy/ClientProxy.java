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

		// ##Items##
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACK.getItem(), new RenderBackPackItem());
//		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKIMPROVED.getItem(), new RenderBackPackItem());
//		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKADVANCED.getItem(), new RenderBackPackItem());
//		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKARMORED.getItem(), new RenderBackPackItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.getItem(), new RenderRocksItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_SLINGSHOT.getItem(), new RenderSlingshotItem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()), new RenderEnchantingItem());

		// ##UPGRADES##
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ANVIL.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BEDROLL.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_BREWINGSTAND.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CAMPFIRE.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_COBBLEGEN.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_CRAFTINGTABLE.getItem(), new RenderUpgradeItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE_ENCHANTINGTABLE.getItem(), new RenderUpgradeItem());

		// ##TileEntities##
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampFire.class, new RenderCampfireTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBedRoll.class, new RenderBedRollTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());

		// ##Entities##
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());

		super.registerItems();
	}

}
