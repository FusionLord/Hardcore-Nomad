package net.firesquared.hardcorenomad;

import net.firesquared.hardcorenomad.helpers.Reference;
import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab
{
	public static final CreativeTabs HardCoreNomadTab = new CreativeTabs(Reference.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return Items.ITEM_BACKPACK.getItem();
		}

		@Override
		public String getTranslatedTabLabel()
		{
			return "HardCore Nomad";
		}
	};

	public static final CreativeTabs HardCoreNomadUpgradesTab = new CreativeTabs(Reference.MOD_ID)
	{
		@Override public Item getTabIconItem() { return Items.ITEM_BACKPACK.getItem();/*ITEM_UPGRADE_BEDROLL_TIER1.getItem();*/ }

		@Override public String getTranslatedTabLabel() { return "HardCore Nomad Upgrades"; }
	};
}
