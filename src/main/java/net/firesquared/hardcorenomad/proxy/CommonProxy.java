package net.firesquared.hardcorenomad.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.player.PlayerEvents;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.world.WorldEvents;
import net.minecraft.item.Item;
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
		GameRegistry.addRecipe(new ItemStack(Items.ITEM_BACKPACKBASIC.getItem(), 1), "sls", "xcx", "wxw", 's', new ItemStack(net.minecraft.init.Items.string), 'l', new ItemStack(net.minecraft.init.Items.leather), 'x', new ItemStack(net.minecraft.init.Items.stick), 'c', new ItemStack(net.minecraft.init.Blocks.chest), 'w', net.minecraft.init.Blocks.wool);
	}

	// Register Dungeon Loot
	public void registerDungeonLoot()
	{
		for(Items item : Items.values())
		{
			LogHelper.debug("Registering Item: " + item.getInternalName());
			ItemStack LootItem = new ItemStack(item.getItem());
			int Max = item.getDungeonChestMax();
			int Min = item.getDungeonChestMin();
			int Weight = item.getWeight();

			if (Min != 0 && Max != 0)
			{
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
