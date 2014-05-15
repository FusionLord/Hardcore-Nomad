package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items {
    // TODO: Add Items
    // Example ITEM_NAME("item.name", new ItemClass())

    ;

    private final String internalName;
    private Item item;

    Items(String internalName, Item item) {
        this.internalName = internalName;
        this.item = item;
        item.setUnlocalizedName(Reference.MOD_ID + internalName);
        //item.setCreativeTab() //TODO: Create a Creative Tab for me to use here...
    }

    public String getInternalName() {
        return internalName;
    }

    public Item getItem() {
        return item;
    }

    public String getStatName() {
        return StatCollector.translateToLocal(item.getUnlocalizedName());
    }

    public ItemStack getDamagedStack(int damage) {
        return new ItemStack(item, 1, damage);
    }

    public ItemStack getSizedStack(int size) {
        return new ItemStack(item, size);
    }
}
