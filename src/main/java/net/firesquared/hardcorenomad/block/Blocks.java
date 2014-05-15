package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.creativetab.CreativeTab;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.StatCollector;

public enum Blocks {
    // TODO: Add Blocks Here
    // Format: BLOCK_NAME("Block.Name", new BlockClass())
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), CreativeTab.TabName)
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class)
    // Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class, CreativeTab.TabName)
    BLOCK_BACKPACK("block.backpack", new BlockBackPack(), CreativeTab.HardCoreNomadTab),

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

    public String getInternalName() {
        return internalName;
    }

    public String getStatName() {
        return StatCollector.translateToLocal(block.getUnlocalizedName().replace("tile.", "block."));
    }

    public Block getBlock() {
        return block;
    }

    public Class<? extends ItemBlock> getItemBlockClass() {
        return itemBlockClass;
    }

    public CreativeTabs getCreativeTabs() {
        return creativeTabs;
    }
}
