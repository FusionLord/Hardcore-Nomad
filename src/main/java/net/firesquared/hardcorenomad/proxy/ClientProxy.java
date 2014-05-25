package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.client.render.*;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBedRoll;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampComp;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampfire;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchanting;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCobbleGenerator;
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
		// ##Items##
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACK.getItem(), RenderCampComp.backpack);
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.getItem(), new RenderRocksItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_SLINGSHOT.getItem(), new RenderSlingshotItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE.getItem(), new RenderUpgradeItem());

		// ##Blocks##
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_BACKPACK.getBlock()), RenderCampComp.backpack);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_BEDROLL.getBlock()), RenderCampComp.bedroll);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_CAMPFIRE.getBlock()), RenderCampComp.campfire);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_COBBLEGEN.getBlock()), RenderCampComp.cobblegen);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.BLOCK_ENCHANTMENTTABLE.getBlock()), RenderCampComp.enchanting);

		// ##TileEntities##
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, RenderCampComp.backpack);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBedRoll.class, RenderCampComp.bedroll);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampFire.class, RenderCampComp.campfire);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCobbleGenerator.class, RenderCampComp.cobblegen);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTable.class, RenderCampComp.enchanting);

		// ##Entities##
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());

		super.registerItems();
	}

}
