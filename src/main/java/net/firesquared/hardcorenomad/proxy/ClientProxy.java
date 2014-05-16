package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.firesquared.hardcorenomad.client.render.RenderBackPack;
import net.firesquared.hardcorenomad.client.render.RenderBackPackTile;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerItems()
	{
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKBASIC.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKIMPROVED.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKADVANCED.getItem(), new RenderBackPack());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACKARMORED.getItem(), new RenderBackPack());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, new RenderBackPackTile());
		super.registerItems();
	}

}
