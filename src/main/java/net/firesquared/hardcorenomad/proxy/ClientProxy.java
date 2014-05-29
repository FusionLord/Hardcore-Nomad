package net.firesquared.hardcorenomad.proxy;

import net.firesquared.hardcorenomad.client.render.*;
import net.firesquared.hardcorenomad.client.render.misc.*;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
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
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_PEBBLE.item, new RenderRocksItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_MISC_SLINGSHOT.item, new RenderSlingshotItem());
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_UPGRADE.item, new RenderUpgradeItem());

		// ##All ItemUpgrades/Camp Components Blocks & TileEntities##
		for(UpgradeType iu : UpgradeType.values())
			if(iu.blockContainer != null && iu.combinedRenderer != null && iu.tileEntityClass != null && iu.levels > 0)
			{
				MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(iu.blockContainer), iu.combinedRenderer);
				ClientRegistry.bindTileEntitySpecialRenderer(iu.tileEntityClass, iu.combinedRenderer);
			}

		// ##Backpack##
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackPack.class, UpgradeType.BACKPACK.combinedRenderer);
		MinecraftForgeClient.registerItemRenderer(Items.ITEM_BACKPACK.item, UpgradeType.BACKPACK.combinedRenderer);

		// ##Entities##
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class, new RenderRocksThrown());

		super.registerItems();
	}

}
