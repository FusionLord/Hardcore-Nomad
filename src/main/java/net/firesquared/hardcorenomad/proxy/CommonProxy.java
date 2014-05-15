package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.player.PlayerEvents;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.world.WorldEvents;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {
    // Register Blocks
    public void registerBlocks() {
        for (Blocks block : Blocks.values()) {
            LogHelper.debug("Registering Block: " + block.getInternalName());
            GameRegistry.registerBlock(block.getBlock().setCreativeTab(block.getCreativeTabs()), block.getItemBlockClass(), "tile." + block.getInternalName());
        }
    }

    // Register TileEntities
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityBackPack.class, "tile.backpack");
    }

    // Register Items
    public void registerItems() {
        for (Items item : Items.values()) {
            LogHelper.debug("Registering Item: " + item.getInternalName());
            Item itemObject = item.getItem();
            itemObject.setTextureName(Reference.MOD_ID + ":" + itemObject.getUnlocalizedName());
            GameRegistry.registerItem(itemObject, item.getInternalName());
        }
    }

    // Register World Events
    public void registerWorldEvents() {
        LogHelper.debug("Registering World Event");
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
    }

	// Register Player Events
	public void registerPlayerEvents() {
		LogHelper.debug("Registering Player Events");
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
	}

	// Register Packet Handler
	public void initPacketHandler()
	{
		Reference.PACKET_HANDLER.initialise();
	}

	public void postInitPacketHandler()
	{
		Reference.PACKET_HANDLER.postInitialise();
	}
}
