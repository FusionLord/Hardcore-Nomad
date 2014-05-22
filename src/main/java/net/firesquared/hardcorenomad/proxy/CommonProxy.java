package net.firesquared.hardcorenomad.proxy;

import java.util.Iterator;
import java.util.List;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.configuration.MainConfiguration;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.entity.EntitySlingShotPebble;
import net.firesquared.hardcorenomad.events.BlockBreakEvent;
import net.firesquared.hardcorenomad.events.PlayerEvents;
import net.firesquared.hardcorenomad.events.WorldEvents;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.item.misc.DispenserBehaviorPebble;
import net.firesquared.hardcorenomad.tile.*;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityEnchantmentTable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public abstract class CommonProxy implements IProxy
{
	// Register Blocks
	public void registerBlocks()
	{
		for(Blocks block : Blocks.values())
		{
			LogHelper.debug("Registering Block: " + block.getInternalName());
			GameRegistry.registerBlock(block.getBlock().setCreativeTab(block.getCreativeTabs()), block.getItemBlockClass(), "tile." + block.getInternalName());
		}
	}

	// Register TileEntities
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityBackPack.class, "tile." + Helper.MOD_ID + ".backpack");
		GameRegistry.registerTileEntity(TileEntityCampFire.class, "tile." + Helper.MOD_ID + ".campfire");
		GameRegistry.registerTileEntity(TileEntityEnchantmentTable.class, "tile." + Helper.MOD_ID + ".enchantmenttable");
		GameRegistry.registerTileEntity(TileEntityCrafting.class, "tile." + Helper.MOD_ID + ".crafting");
		GameRegistry.registerTileEntity(TileEntityBedRoll.class, "tile." + Helper.MOD_ID + ".bedroll");
	}

	// Register Entities
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityPebble.class, "entity.pebble", 0, HardcoreNomad.instance, 64, 1, true);
		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.ITEM_MISC_PEBBLE.getItem(), new DispenserBehaviorPebble());
		EntityRegistry.registerModEntity(EntitySlingShotPebble.class, "entity.slingshotpebble", 0, HardcoreNomad.instance, 64, 1, true);
	}

	// Register Items
	public void registerItems()
	{
		for(Items item : Items.values())
		{
			LogHelper.debug("Registering Item: " + item.getInternalName());
			Item itemObject = item.getItem();
			itemObject.setTextureName(Helper.MOD_ID + ":" + itemObject.getUnlocalizedName());
			GameRegistry.registerItem(itemObject, item.getInternalName());
		}
	}

	// Register World Events
	public void registerWorldEvents()
	{
		LogHelper.debug("Registering World Event");
		MinecraftForge.EVENT_BUS.register(new WorldEvents());
	}

	// Register Player Events
	public void registerPlayerEvents()
	{
		LogHelper.debug("Registering Player Events");
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
	}

	// Register Misc Events
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new BlockBreakEvent());
	}

	// Register Packet Handler
	public void initPacketHandler()
	{
		Helper.PACKET_HANDLER.initialise();
	}

	public void postInitPacketHandler()
	{
		Helper.PACKET_HANDLER.postInitialise();
	}

	// Register Recipes
	public void registerRecipes() {

		if (MainConfiguration.CONFIG_WOODTOOLSDISABLED)
		{
			removeWoodenTools();
		}

		if (!MainConfiguration.CONFIG_ALTRECIPEFORPEBBLES)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.ITEM_MISC_PEBBLE.getItem(), 9), new Object[] { "c", 'c', net.minecraft.init.Blocks.cobblestone});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.ITEM_MISC_PEBBLE.getItem(), 9), new Object[] { "c", 'c', net.minecraft.init.Blocks.stone});
		}

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_BACKPACK.getItem(), 0), "sls", "xcx", "wxw", 's', new ItemStack(net.minecraft.init.Items.string), 'l', new ItemStack(net.minecraft.init.Items.leather), 'x', new ItemStack(net.minecraft.init.Items.stick), 'c', new ItemStack(net.minecraft.init.Blocks.chest), 'w', net.minecraft.init.Blocks.wool);

//		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", " s ", " l ", 'b', new ItemStack(net.minecraft.init.Items.bow), 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log);
//		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", " s ", " l ", 'b', new ItemStack(net.minecraft.init.Items.bow), 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log2);
//		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "sts", "twt", "sts", 's', new ItemStack(net.minecraft.init.Items.stick), 't', net.minecraft.init.Blocks.sapling, 'w', net.minecraft.init.Blocks.planks);

		// ====== CRAFTING TABLE ======
		// Crafting Table Tier 1
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Crafting, 0), " s ", "scs", " s ", 's', new ItemStack(net.minecraft.init.Items.stick), 'c', new ItemStack(net.minecraft.init.Blocks.crafting_table));

		// ====== COBBLESTONE GENERATOR ======
		// CobbleGen Level 1
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CobbleGen, 0), "rpr", "lhw", "scs", 'r', new ItemStack(net.minecraft.init.Items.repeater), 'p', new ItemStack(net.minecraft.init.Blocks.piston), 'l', new ItemStack(net.minecraft.init.Items.lava_bucket), 'h', new ItemStack(net.minecraft.init.Blocks.hopper), 'w', new ItemStack(net.minecraft.init.Items.water_bucket), 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Blocks.chest));

		// ====== BREWING STAND ======
		// Brewing Stand Level 1
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BrewingStand, 0), " b ", "fwf", "scs", 'b', new ItemStack(net.minecraft.init.Items.bone), 'f', new ItemStack(net.minecraft.init.Blocks.fence), 'w', new ItemStack(net.minecraft.init.Items.water_bucket), 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Items.cauldron));

		// ====== FIREPIT ======
		//                                             Campfire Level indicated but n+1 here v       and    here v
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CampFire, 0), " t ", "tst", "sls", 't', net.minecraft.init.Blocks.sapling, 's', new ItemStack(net.minecraft.init.Items.stick), 'l', "woodLog"));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CampFire, 1), "scs", "cac", "scs", 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Blocks.cobblestone), 'a', new ItemStack(net.minecraft.init.Blocks.sand));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CampFire, 2), "sss", "ftf", "f f", 's', new ItemStack(net.minecraft.init.Items.reeds), 'f', new ItemStack(net.minecraft.init.Blocks.fence), 't', new ItemStack(net.minecraft.init.Items.string));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CampFire, 3), " c ", "cbc", "bob", 'c', new ItemStack(net.minecraft.init.Items.clay_ball), 'b', new ItemStack(net.minecraft.init.Blocks.cobblestone), 'o', new ItemStack(net.minecraft.init.Blocks.coal_block));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.CampFire, 4), "sl ", "ttl", "lll", 's', new ItemStack(net.minecraft.init.Items.stick), 'l', new ItemStack(net.minecraft.init.Items.leather), 't', new ItemStack(net.minecraft.init.Items.string));
		// ====== ENCHANTMENT TABLE ======
		//                                                          Enchantment Table Level indicated but n+1 here v
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Enchanting, 0), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.iron_ingot), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', "woodLog"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Enchanting, 1), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.gold_ingot), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', "woodLog"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Enchanting, 2), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.diamond), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', "woodLog"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Enchanting, 3), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.emerald), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', "woodLog"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.Enchanting, 4), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.emerald), 'l', new ItemStack(net.minecraft.init.Blocks.iron_block), 'o', "woodLog"));

		// ====== BED ======
		//                                   Enchantment Table Level indicated but n+1 here v
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BedRoll, 0), "   ", "www", "ppp", 'w', net.minecraft.init.Blocks.wool, 'p', net.minecraft.init.Blocks.planks);
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BedRoll, 1), "   ", "www", "ppp", 'w', net.minecraft.init.Blocks.wool, 'p', new ItemStack(net.minecraft.init.Blocks.hay_block));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BedRoll, 2), "   ", "www", "ppp", 'w', net.minecraft.init.Blocks.wool, 'p', new ItemStack(net.minecraft.init.Blocks.cobblestone));
		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BedRoll, 3), "   ", "www", "ppp", 'w', net.minecraft.init.Blocks.wool, 'p', new ItemStack(net.minecraft.init.Blocks.stone));
//		GameRegistry.addRecipe(ItemUpgrade.getUpgradeStack(ItemUpgrade.UpgradeType.BedRoll, 5), "   ", "www", "ppp", 'w', net.minecraft.init.Blocks.wool, 'p', new ItemStack(net.minecraft.init.Items.iron_ingot));

		// ====== BLOCKS ======
		// Cobblestone Recipe
		GameRegistry.addRecipe(new ItemStack(net.minecraft.init.Blocks.cobblestone, 0), "ppp", "ppp", "ppp", 'p', new ItemStack(Items.ITEM_MISC_PEBBLE.getItem()));
	}

	private void removeWoodenTools()
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		Iterator<IRecipe> iterator = recipes.iterator();

		while (iterator.hasNext()) {
			ItemStack is = iterator.next().getRecipeOutput();
			if (is != null &&
					(is.getItem() == net.minecraft.init.Items.wooden_axe
					|| is.getItem() == net.minecraft.init.Items.wooden_pickaxe
					|| is.getItem() == net.minecraft.init.Items.wooden_hoe
					|| is.getItem() == net.minecraft.init.Items.wooden_sword)
				)
			{
				iterator.remove();
			}
		};
	}
	
	// Register Dungeon Loot
	public void registerDungeonLoot()
	{
		for(Items item : Items.values())
		{
			ItemStack LootItem = new ItemStack(item.getItem());
			int Max = item.getDungeonChestMax();
			int Min = item.getDungeonChestMin();
			int Weight = item.getWeight();

			if (Min != 0 && Max != 0)
			{
				LogHelper.debug("Registering DungeonLoot: " + item.getInternalName());
				ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
				ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
				ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
				ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
				ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
				ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(LootItem, Min, Max, Weight));
			}
		}
	}
}
