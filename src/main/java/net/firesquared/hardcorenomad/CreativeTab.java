package net.firesquared.hardcorenomad;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab
{
	public static final CreativeTabs HardCoreNomadTab = new CreativeTabs(Helper.Strings.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return Items.ITEM_BACKPACK.item;
		}

		@Override
		public String getTranslatedTabLabel()
		{
			return "HardCore Nomad";
		}
	};

	public static final CreativeTabs HardCoreNomadUpgradesTab = new CreativeTabs(Helper.Strings.MOD_ID)
	{
		@Override
		public Item getTabIconItem()
		{
			return Items.ITEM_UPGRADE.item;
		}

		@Override
		public ItemStack getIconItemStack()
		{
			return ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CAMPFIRE, 2);
		}

		@Override
		public String getTranslatedTabLabel()
		{
			return "HardCore Nomad Upgrades";
		}
	};
}
