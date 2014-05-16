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
import net.firesquared.hardcorenomad.item.upgrades.*;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items
{
	// TODO: Add Items
	// Example ITEM_NAME("item.name", new ItemClass())
	ITEM_BACKPACKBASIC("backpack.basic", new ItemBackPackBasic(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKIMPROVED("backpack.improved", new ItemBackPackImproved(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKADVANCED("backpack.advanced", new ItemBackPackAdvanced(0), CreativeTab.HardCoreNomadTab),
	ITEM_BACKPACKARMORED("backpack.armored", new ItemBackPackArmored(0), CreativeTab.HardCoreNomadTab),
	//ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
	ITEM_FIREBUNDLE("campfire.bundle", new ItemFireBundle(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGFIRSTAID("healing.firstaid", new ItemHealingFirstAid(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGHERB("healing.herb", new ItemHealingHerb(2, 1.0F, false), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGMAGICALAID("healing.magicalaid", new ItemHealingMagicalAid(), CreativeTab.HardCoreNomadTab),

	//Upgrade Items
	//Bedroll Upgrades
	ITEM_UPGRADE_BEDROLL_TIER1("upgrade.bedroll.tier1", new itemUpgradeTier1BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER2("upgrade.bedroll.tier2", new itemUpgradeTier2BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER3("upgrade.bedroll.tier3", new itemUpgradeTier3BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER4("upgrade.bedroll.tier4", new itemUpgradeTier4BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER5("upgrade.bedroll.tier5", new itemUpgradeTier5BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),

	//Camp Fire Upgrades
	ITEM_UPGRADE_CAMPFIRE_TIER1("upgrade.campfire.tier1", new itemUpgradeTier1CampFire(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CAMPFIRE_TIER2("upgrade.campfire.tier2", new itemUpgradeTier2CampFire(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CAMPFIRE_TIER3("upgrade.campfire.tier3", new itemUpgradeTier3CampFire(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CAMPFIRE_TIER4("upgrade.campfire.tier4", new itemUpgradeTier4CampFire(), CreativeTab.HardCoreNomadUpgradesTab),

	//Enchantment Table Upgrades
	ITEM_UPGRADE_ENCHANTTABLE_TIER1("upgrade.enchanttable.tier1", new itemUpgradeTier1BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTTABLE_TIER2("upgrade.enchanttable.tier2", new itemUpgradeTier2BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTTABLE_TIER3("upgrade.enchanttable.tier3", new itemUpgradeTier3BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTTABLE_TIER4("upgrade.enchanttable.tier4", new itemUpgradeTier4BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTTABLE_TIER5("upgrade.enchanttable.tier5", new itemUpgradeTier5BedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
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
