package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.creativetab.CreativeTab;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackAdvanced;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackArmored;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackBasic;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackImproved;
import net.firesquared.hardcorenomad.item.campfire.ItemFireBundle;
import net.firesquared.hardcorenomad.item.healing.ItemHealingFirstAid;
import net.firesquared.hardcorenomad.item.healing.ItemHealingHerb;
import net.firesquared.hardcorenomad.item.healing.ItemHealingMagicalAid;
import net.firesquared.hardcorenomad.item.upgrades.itemUpgradeTier1BedRoll;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items
{
	// TODO: Add Items
	// Example ITEM_NAME("item.name", new ItemClass())
	ITEM_BACKPACKBASIC("backpackbasic", new ItemBackPackBasic(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKIMPROVED("backpackimproved", new ItemBackPackImproved(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKADVANCED("backpackadvanced", new ItemBackPackAdvanced(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKARMORED("backpackarmored", new ItemBackPackArmored(0), CreativeTab.HardCoreNomadTab),
	//ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
	ITEM_FIREBUNDLE("firebundle", new ItemFireBundle(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGFIRSTAID("healingfirstaid", new ItemHealingFirstAid(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGHERB("healingherb", new ItemHealingHerb(2, 1.0F, false), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGMAGICALAID("healingmagicalaid", new ItemHealingMagicalAid(), CreativeTab.HardCoreNomadTab),

	//Upgrade Items
	ITEM_UPGRADE_BEDROLL_TIER1("upgrade.bedroll.tier1", new itemUpgradeTier1BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),

	;

	private final String internalName;
	private Item item;

	Items(String internalName, Item item, CreativeTabs creativeTabs)
	{
		this.internalName = internalName;
		this.item = item;
		item.setUnlocalizedName(Reference.MOD_ID + "." + internalName);
		item.setCreativeTab(creativeTabs);
	}

	public String getInternalName()
	{
		return internalName;
	}

	public Item getItem()
	{
		return item;
	}

	public String getStatName()
	{
		return StatCollector.translateToLocal(item.getUnlocalizedName());
	}

	public ItemStack getDamagedStack(int damage)
	{
		return new ItemStack(item, 1, damage);
	}

	public ItemStack getSizedStack(int size)
	{
		return new ItemStack(item, size);
	}
}
