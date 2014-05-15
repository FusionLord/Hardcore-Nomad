package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.item.Item;

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
            Item itemObject = item.getItem();
            itemObject.setTextureName(Reference.MOD_ID + ":" + itemObject.getUnlocalizedName());
            GameRegistry.registerItem(itemObject, item.getInternalName());
        }
    }
}
