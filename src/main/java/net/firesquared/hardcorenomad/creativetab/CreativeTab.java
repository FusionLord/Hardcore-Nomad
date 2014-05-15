package net.firesquared.hardcorenomad.creativetab;

import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab {
    public static final CreativeTabs HardCoreNomadTab = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.compass;
        }

        @Override
        public String getTranslatedTabLabel() {
            return "HardCore Nomad";
        }
    };
}
