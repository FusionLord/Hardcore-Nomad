package net.firesquared.hardcorenomad.item.upgrades;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemUpgrade extends Item
{
	private UpgradeType upgradeType;

	public ItemUpgrade(UpgradeType upgradeType)
	{
		super();
		this.upgradeType = upgradeType;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < getUpgradeType().getLevelCount(); i++)
		{
			list.add(new ItemStack(this, 0, i));
		}
	}

	public static int getUpgradeLevel(int meta)
	{
		return meta + 1;
	}

	public UpgradeType getUpgradeType()
	{
		return this.upgradeType;
	}
}
