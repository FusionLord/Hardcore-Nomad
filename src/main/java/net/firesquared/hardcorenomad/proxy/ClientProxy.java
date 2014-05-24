package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.client.render.*;
import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPackItem;
import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPackTile;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBedRoll;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampfire;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchanting;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityEnchantmentTable;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerItems()
	{
		IItemRenderer backpack = new RenderBackPackItem();
		// ##Items##
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACK.getItem(), backpack);
		
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.getItem(), new RenderRocksItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_SLINGSHOT.getItem(), new RenderSlingshotItem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()), new RenderEnchanting());

		// ##UPGRADES##
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE.getItem(), new RenderUpgradeItem(backpack));

		// ##TileEntities##
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchanting());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampFire.class, new RenderCampfire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBedRoll.class, new RenderBedRoll());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, new RenderEnchanting());

		// ##Entities##
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());

		super.registerItems();
	}

}
