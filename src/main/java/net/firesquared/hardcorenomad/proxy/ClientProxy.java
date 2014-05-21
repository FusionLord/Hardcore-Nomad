package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.client.render.*;
import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPackItem;
import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPackTile;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBedRollTile;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampfireTile;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchantingItem;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchantingTile;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.helpers.ModelRegistry;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityEnchantmentTable;
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
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE.getItem(), new RenderUpgradeItem());

		// ##TileEntities##
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampFire.class, new RenderCampfireTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBedRoll.class, new RenderBedRollTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchantingTile());

		// ##Entities##
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());

		super.registerItems();
	}

}
