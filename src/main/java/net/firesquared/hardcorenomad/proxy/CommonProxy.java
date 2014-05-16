package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.player.PlayerEvents;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.world.WorldEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

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
		GameRegistry.registerTileEntity(TileEntityBackPack.class, "tile.backpack");
	}

	// Register Entities
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityPebble.class, "entity.pebble", 0, HardcoreNomad.instance, 64, 1, true);
	}

	// Register Items
	public void registerItems()
	{
		for(Items item : Items.values())
		{
			LogHelper.debug("Registering Item: " + item.getInternalName());
			Item itemObject = item.getItem();
			itemObject.setTextureName(Reference.MOD_ID + ":" + itemObject.getUnlocalizedName());
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

	// Register Packet Handler
	public void initPacketHandler()
	{
		Reference.PACKET_HANDLER.initialise();
	}

	public void postInitPacketHandler()
	{
		Reference.PACKET_HANDLER.postInitialise();
	}

	// Register Recipes
	public void registerRecipes() {
		// BackPacks
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_BACKPACKBASIC.getItem(), 1), "sls", "xcx", "wxw", 's', new ItemStack(net.minecraft.init.Items.string), 'l', new ItemStack(net.minecraft.init.Items.leather), 'x', new ItemStack(net.minecraft.init.Items.stick), 'c', new ItemStack(net.minecraft.init.Blocks.chest), 'w', net.minecraft.init.Blocks.wool);

		// ?
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", " s ", " l ", 'b', new ItemStack(net.minecraft.init.Items.bow), 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log);
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", " s ", " l ", 'b', new ItemStack(net.minecraft.init.Items.bow), 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log2);

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "sts", "twt", "sts", 's', new ItemStack(net.minecraft.init.Items.stick), 't', net.minecraft.init.Blocks.sapling, 'w', net.minecraft.init.Blocks.planks);

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " t ", "tst", "sls", 't', net.minecraft.init.Blocks.sapling, 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log);
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " t ", "tst", "sls", 't', net.minecraft.init.Blocks.sapling, 's', new ItemStack(net.minecraft.init.Items.stick), 'l', net.minecraft.init.Blocks.log2);

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "scs", "cac", "scs", 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Blocks.cobblestone), 'a', new ItemStack(net.minecraft.init.Blocks.sand));

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "sss", "ftf", "f f", 's', new ItemStack(net.minecraft.init.Items.reeds), 'f', new ItemStack(net.minecraft.init.Blocks.fence), 't', new ItemStack(net.minecraft.init.Items.string));

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " c ", "cbc", "bob", 'c', new ItemStack(net.minecraft.init.Items.clay_ball), 'b', new ItemStack(net.minecraft.init.Blocks.cobblestone), 'o', new ItemStack(net.minecraft.init.Blocks.coal_block));

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "sl ", "ttl", "lll", 's', new ItemStack(net.minecraft.init.Items.stick), 'l', new ItemStack(net.minecraft.init.Items.leather), 't', new ItemStack(net.minecraft.init.Items.string));

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "rpr", "lhw", "scs", 'r', new ItemStack(net.minecraft.init.Items.repeater), 'p', new ItemStack(net.minecraft.init.Blocks.piston), 'l', new ItemStack(net.minecraft.init.Items.lava_bucket), 'h', new ItemStack(net.minecraft.init.Blocks.hopper), 'w', new ItemStack(net.minecraft.init.Items.water_bucket), 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Blocks.chest));

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.iron_ingot), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', net.minecraft.init.Blocks.log);
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", "ili", "ooo", 'b', new ItemStack(net.minecraft.init.Items.book), 'i', new ItemStack(net.minecraft.init.Items.iron_ingot), 'l', new ItemStack(net.minecraft.init.Blocks.lapis_block), 'o', net.minecraft.init.Blocks.log2);

		GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), " b ", "fwf", "scs", 'b', new ItemStack(net.minecraft.init.Items.bone), 'f', new ItemStack(net.minecraft.init.Blocks.fence), 'w', new ItemStack(net.minecraft.init.Items.water_bucket), 's', new ItemStack(net.minecraft.init.Blocks.stone), 'c', new ItemStack(net.minecraft.init.Items.cauldron));

		//GameRegistry.addRecipe(new ItemStack(Items.ITEM_FIREBUNDLE.getItem(), 1), "   ", "geg", "   ", 'g', new ItemStack(net.minecraft.init.Items.gold_ingot), net.minecraft.init.Items.enchanted_book);
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
