package net.firesquared.hardcorenomad.helpers.enums;

import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.CreativeTab;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.item.*;
import net.firesquared.hardcorenomad.item.backpacks.*;
import net.firesquared.hardcorenomad.item.misc.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items
{
	// TODO: Add Items
	ITEM_BACKPACK("item.backpack", new ItemBackPack(), CreativeTab.HardCoreNomadTab),

	//ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
	//ITEM_FIREBUNDLE("campfire.bundle", new ItemFireBundle(), CreativeTab.HardCoreNomadTab),

	ITEM_HEALING("healing", new ItemHealing(), CreativeTab.HardCoreNomadTab),
	
	ITEM_FLINTSHEARS("tools.flintshears", new ItemShears().setMaxDamage(42).setTextureName("hardcorenomad:flintshears"), CreativeTabs.tabTools),

	ITEM_MISC_PEBBLE("misc.pebble", new ItemPebble(), CreativeTab.HardCoreNomadTab),
	ITEM_MISC_SLINGSHOT("misc.slingshot", new ItemSlingShot(), CreativeTab.HardCoreNomadTab),

	//Upgrade Items
	ITEM_UPGRADE("upgrade", new ItemUpgrade(), CreativeTab.HardCoreNomadUpgradesTab)

	;

	private final String internalName;
	public final Item item;

	Items(String internalName, Item item, CreativeTabs creativeTabs)
	{
		this.internalName = internalName;
		this.item = item.setTextureName(Helper.Strings.MOD_ID + ":" + internalName);
		item.setUnlocalizedName(Helper.Strings.MOD_ID + "." + internalName);
		item.setCreativeTab(creativeTabs);
	}
	
	private void register()
	{
		Helper.getNomadLogger().debug("Registering Item: " + internalName);
		GameRegistry.registerItem(item, internalName);
	}

	public String getInternalName()
	{
		return internalName;
	}

	public String getStatName()
	{
		return StatCollector.translateToLocal(item.getUnlocalizedName());
	}
	
	public ItemStack getStack(int damage, int size)
	{
		return new ItemStack(item, size, damage);
	}

	public static void registerAll()
	{
		for(Items i : Items.values())
			i.register();
	}
}
