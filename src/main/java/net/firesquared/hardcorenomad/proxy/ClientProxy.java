package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.client.render.RenderBackPack;
import net.firesquared.hardcorenomad.client.render.RenderBackPackTile;
import net.firesquared.hardcorenomad.client.render.RenderBedRollTile;
import net.firesquared.hardcorenomad.client.render.RenderCampfireTile;
import net.firesquared.hardcorenomad.client.render.RenderEnchantingItem;
import net.firesquared.hardcorenomad.client.render.RenderEnchantingTile;
import net.firesquared.hardcorenomad.client.render.RenderRocks;
import net.firesquared.hardcorenomad.client.render.RenderRocksThrown;
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
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKBASIC.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKIMPROVED.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKADVANCED.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKARMORED.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.getItem(), new RenderRocks());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()), new RenderEnchantingItem());
		
		
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
