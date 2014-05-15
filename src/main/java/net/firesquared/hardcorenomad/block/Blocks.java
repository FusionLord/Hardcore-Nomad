package net.firesquared.HardcoreNomad.block;

import net.firesquared.HardcoreNomad.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public enum Blocks {
    // TODO: Add Blocks Here
    // Format: BLOCK_NAME("Block.Name", new BlockClass())
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), CreativeTab.TabName)
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class)
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class, CreativeTab.TabName)

    ;

    private final String internalName;
    private Block block;
    private Class<? extends ItemBlock> itemBlockClass;
    private CreativeTabs creativeTabs;

    Blocks(String internalName, Block block) {
        this(internalName, block, ItemBlock.class, null);
    }

    Blocks(String internalName, Block block, CreativeTabs creativeTabs) {
        this(internalName, block, ItemBlock.class, creativeTabs);
    }

    Blocks(String internalName, Block block, Class<? extends ItemBlock> itemBlockClass) {
        this(internalName, block, itemBlockClass, null);
    }

    Blocks(String internalName, Block block, Class<? extends ItemBlock> itemBlockClass, CreativeTabs creativeTabs) {
        this.internalName = internalName;
        this.block = block;
        this.itemBlockClass = itemBlockClass;
        this.creativeTabs = creativeTabs;
        block.setBlockName(Reference.MOD_ID + internalName);
    }
}
