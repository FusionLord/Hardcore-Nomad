package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;

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
        
    }

    // Register Items
    public void registerItems() {
        for (Items item : Items.values()) {
            LogHelper.debug("Registering Item: " + item.getInternalName());
            GameRegistry.registerItem(item.getItem(), item.getInternalName());
        }
    }
}
