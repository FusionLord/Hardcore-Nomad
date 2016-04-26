package net.fusionlord.hardcorenomad.init;

import net.fusionlord.hardcorenomad.common.blocks.BlockAnvil;
import net.fusionlord.hardcorenomad.common.blocks.BlockBackpack;
import net.fusionlord.hardcorenomad.common.blocks.BlockUpgradable;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.items.ItemBlockUpgradable;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FusionLord on 4/25/2016.
 */
public class ModBlocks
{
	public final static BlockAnvil anvil = new BlockAnvil();
	public final static BlockBackpack backpack = new BlockBackpack();

	public static void registerBlocks()
	{
		for (Block block : getBlocks())
		{
			if(block instanceof BlockUpgradable)
			{
				BlockUpgradable blockUpgradable = (BlockUpgradable) block;
				register(blockUpgradable, new ItemBlockUpgradable(blockUpgradable));
			}
			else
			{
				register(block);
			}
		}
	}

	private static void register(Block block)
	{
		register(block, new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	private static void register(Block block, Item itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		if (block instanceof ITileEntityProvider)
		{
			GameRegistry.registerTileEntity(((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass(), "tileentity." + block.getRegistryName());
		}
		LogHelper.info("Registering block object: " + block.getRegistryName());
	}

	public static void registerRenders()
	{
		for(Block block : getBlocks())
		{
			if(block instanceof BlockUpgradable)
			{
				BlockUpgradable blockUpgradable = (BlockUpgradable) block;
				for(EnumUpgrade upgrade : blockUpgradable.getValidLevels())
				{
					LogHelper.info("Registering inventory renderer: " + block.getRegistryName() + "#level=" + upgrade.getName());
					regRender(block, upgrade.ordinal(), "STUPIDVARIANT");
				}
			}
		}
	}

	private static void regRender(Block block, int meta, String variant)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("hardcorenomad:anvil", variant));
	}

	private static void regRender(Block block, int meta)
	{
		regRender(block, meta, "inventory");
	}

	private static List<Block> getBlocks()
	{
		List<Block> blockList = new ArrayList<Block>();
		for(Field field : ModBlocks.class.getFields())
		{
			try
			{
				if (Block.class.isInstance(field.get(ModBlocks.class)))
					blockList.add((Block) field.get(ModBlocks.class));
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return blockList;
	}
}
