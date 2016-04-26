package net.fusionlord.hardcorenomad.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by FusionLord on 4/26/2016.
 */
public class ModCreativeTab extends CreativeTabs
{
	public static CreativeTabs INSTANCE;

	static {
		new ModCreativeTab();
	}

	public ModCreativeTab()
	{
		super("hardcorenomad.blocks");
		INSTANCE = this;
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(ModBlocks.anvil);
	}
}
