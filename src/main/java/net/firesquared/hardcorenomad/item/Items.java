package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.creativetab.CreativeTab;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items {
    // TODO: Add Items
    // Example ITEM_NAME("item.name", new ItemClass())
    ITEM_BACKPACKBASIC("item.backpackbasic", new ItemBackPackBasic(0)),
    ITEM_BACKPACKIMPROVED("item.backpackimproved", new ItemBackPackImproved(0)),
    ITEM_BACKPACKADVANCED("item.backpackadvanced", new ItemBackPackAdvanced(0)),
    ITEM_BACKPACKARMORED("item.backpackarmored", new ItemBackPackArmored(0)),
    //ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
    ITEM_FIREBUNDLE("item.firebundle", new ItemFireBundle()),
    ITEM_HEALINGFIRSTAID("item.healingfirstaid", new ItemHealingFirstAid()),
    ITEM_HEALINGHERB("item.healingherb", new ItemHealingHerb()),
    ITEM_HEALINGMAGICALAID("item.healingmagicalaid", new ItemHealingMagicalAid()),
    ;

    private final String internalName;
    private Item item;

    Items(String internalName, Item item) {
        this.internalName = internalName;
        this.item = item;
        item.setUnlocalizedName(Reference.MOD_ID + internalName);
        item.setCreativeTab(CreativeTab.HardCoreNomadTab);
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
