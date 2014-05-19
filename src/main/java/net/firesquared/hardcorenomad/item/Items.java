package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.creativetab.CreativeTab;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackAdvanced;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackArmored;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackBasic;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPackImproved;
import net.firesquared.hardcorenomad.item.healing.ItemHealingHerb;
import net.firesquared.hardcorenomad.item.misc.ItemPebble;
import net.firesquared.hardcorenomad.item.misc.ItemSlingShot;
import net.firesquared.hardcorenomad.item.upgrades.*;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum Items
{
	// TODO: Add Items
	// Example ITEM_NAME("item.name", new ItemClass())
	ITEM_BACKPACKBASIC("backpack.basic", new ItemBackPackBasic(0), CreativeTab.HardCoreNomadTab, 1, 1, 10),
	ITEM_BACKPACKIMPROVED("backpack.improved", new ItemBackPackImproved(0), CreativeTab.HardCoreNomadTab, 1, 1, 4),
	ITEM_BACKPACKADVANCED("backpack.advanced", new ItemBackPackAdvanced(0), CreativeTab.HardCoreNomadTab, 1, 1, 3),
	ITEM_BACKPACKARMORED("backpack.armored", new ItemBackPackArmored(0), CreativeTab.HardCoreNomadTab, 1, 1, 1),
	//ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
	//ITEM_FIREBUNDLE("campfire.bundle", new ItemFireBundle(), CreativeTab.HardCoreNomadTab),
	//ITEM_HEALINGFIRSTAID("healing.firstaid", new ItemHealingFirstAid(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGHERB("healing.herb", new ItemHealingHerb(2, 1.0F, false), CreativeTab.HardCoreNomadTab, 1, 5, 4),
	//ITEM_HEALINGMAGICALAID("healing.magicalaid", new ItemHealingMagicalAid(), CreativeTab.HardCoreNomadTab),
	ITEM_FLINTSHEARS("tools.flintshears", new ItemShears().setMaxDamage(42).setTextureName("hardcorenomad:flintshears"), CreativeTabs.tabTools),

	ITEM_MISC_PEBBLE("misc.pebble", new ItemPebble(), CreativeTab.HardCoreNomadTab, 1, 23, 5),
	ITEM_MISC_SLINGSHOT("misc.slingshot", new ItemSlingShot(), CreativeTab.HardCoreNomadTab),
	//Upgrade Items
	//Bedroll Upgrades
	ITEM_UPGRADE_BEDROLL_TIER1("upgrade.bedroll.tier1", new ItemUpgradeBedRollTier1(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 3),
	ITEM_UPGRADE_BEDROLL_TIER2("upgrade.bedroll.tier2", new ItemUpgradeBedRollTier2(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 1),
	ITEM_UPGRADE_BEDROLL_TIER3("upgrade.bedroll.tier3", new ItemUpgradeBedRollTier3(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER4("upgrade.bedroll.tier4", new ItemUpgradeBedRollTier4(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL_TIER5("upgrade.bedroll.tier5", new ItemUpgradeBedRollTier5(), CreativeTab.HardCoreNomadUpgradesTab),

	//Camp Fire Upgrades
	ITEM_UPGRADE_CAMPFIRE_TIER1("upgrade.campfire.tier1", new ItemUpgradeCampFireTier1(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 3),
	ITEM_UPGRADE_CAMPFIRE_TIER2("upgrade.campfire.tier2", new ItemUpgradeCampFireTier2(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 1),
	ITEM_UPGRADE_CAMPFIRE_TIER3("upgrade.campfire.tier3", new ItemUpgradeCampFireTier3(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CAMPFIRE_TIER4("upgrade.campfire.tier4", new ItemUpgradeCampFireTier4(), CreativeTab.HardCoreNomadUpgradesTab),

	//Enchantment Table Upgrades
	ITEM_UPGRADE_ENCHANTTABLE_TIER1("upgrade.enchanttable.tier1", new ItemUpgradeEnchantTableTier1(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 3),
	ITEM_UPGRADE_ENCHANTTABLE_TIER2("upgrade.enchanttable.tier2", new ItemUpgradeEnchantTableTier2(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 2),
	ITEM_UPGRADE_ENCHANTTABLE_TIER3("upgrade.enchanttable.tier3", new ItemUpgradeEnchantTableTier3(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 1),
	ITEM_UPGRADE_ENCHANTTABLE_TIER4("upgrade.enchanttable.tier4", new ItemUpgradeEnchantTableTier4(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTTABLE_TIER5("upgrade.enchanttable.tier5", new ItemUpgradeEnchantTableTier5(), CreativeTab.HardCoreNomadUpgradesTab),

	ITEM_UPGRADE_COBBLEGENERATOR_TIER1("upgrade.cobblegenerator.tier1", new ItemUpgradeCobbleGenTier1(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 2),

	ITEM_UPGRADE_CRAFTINGTABLE_TIER1("upgrade.craftingtable.tier1", new ItemUpgradeCraftingTableTier1(), CreativeTab.HardCoreNomadUpgradesTab, 1, 1, 5),

	ITEM_UPGRADE_BREWINGSTAND_TIER1("upgrade.brewingstand.tier1", new ItemUpgradeBrewingStandTier1(), CreativeTab.HardCoreNomadUpgradesTab),


	;

	private final String internalName;
	private Item item;
	private int dungeonChestMin;
	private int dungeonChestMax;
	private int weight;

	Items(String internalName, Item item, CreativeTabs creativeTabs)
	{
		this(internalName, item, creativeTabs, 0, 0, 0);
	}

	Items(String internalName, Item item, CreativeTabs creativeTabs, int dungeonChestMin, int dungeonChestMax, int weight) {
		this.internalName = internalName;
		this.item = item;
		item.setUnlocalizedName(Reference.MOD_ID + "." + internalName);
		item.setCreativeTab(creativeTabs);
		this.dungeonChestMax = dungeonChestMax;
		this.dungeonChestMin = dungeonChestMin;
		this.weight = weight;
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

	public int getDungeonChestMin() {
		return this.dungeonChestMin;
	}

	public int getDungeonChestMax() {
		return this.dungeonChestMax;
	}

	public int getWeight() {
		return this.weight;
	}
}
