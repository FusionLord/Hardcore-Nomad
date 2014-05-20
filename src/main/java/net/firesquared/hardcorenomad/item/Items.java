package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.creativetab.CreativeTab;
import net.firesquared.hardcorenomad.item.backpacks.*;
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
	ITEM_BACKPACK("item.backpack", new ItemBackPack(), CreativeTab.HardCoreNomadTab),
	//ITEM_FIREBOW("item.firebow", new ItemFireBow(0.0F, null, null)),
	//ITEM_FIREBUNDLE("campfire.bundle", new ItemFireBundle(), CreativeTab.HardCoreNomadTab),
	//ITEM_HEALINGFIRSTAID("healing.firstaid", new ItemHealingFirstAid(), CreativeTab.HardCoreNomadTab),
	ITEM_HEALINGHERB("healing.herb", new ItemHealingHerb(2, 1.0F, false), CreativeTab.HardCoreNomadTab, 1, 5, 4),
	//ITEM_HEALINGMAGICALAID("healing.magicalaid", new ItemHealingMagicalAid(), CreativeTab.HardCoreNomadTab),
	ITEM_FLINTSHEARS("tools.flintshears", new ItemShears().setMaxDamage(42).setTextureName("hardcorenomad:flintshears"), CreativeTabs.tabTools),

	ITEM_MISC_PEBBLE("misc.pebble", new ItemPebble(), CreativeTab.HardCoreNomadTab, 1, 23, 5),
	ITEM_MISC_SLINGSHOT("misc.slingshot", new ItemSlingShot(), CreativeTab.HardCoreNomadTab),

	//Upgrade Items
	ITEM_UPGRADE_ANVIL("anvil", new ItemUpgradeAnvil(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BEDROLL("bedroll", new ItemUpgradeBedRoll(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_BREWINGSTAND("brewingstand", new ItemUpgradeBrewingStand(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CAMPFIRE("campfire", new ItemUpgradeCampFire(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_COBBLEGEN("cobblegen", new ItemUpgradeCobbleGen(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_CRAFTINGTABLE("craftingtable", new ItemUpgradeCraftingTable(), CreativeTab.HardCoreNomadUpgradesTab),
	ITEM_UPGRADE_ENCHANTINGTABLE("enchantingtable", new ItemUpgradeEnchantingTable(), CreativeTab.HardCoreNomadUpgradesTab),

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
