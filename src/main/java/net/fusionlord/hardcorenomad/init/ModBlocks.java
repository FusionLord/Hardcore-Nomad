package net.fusionlord.hardcorenomad.init;

import net.fusionlord.hardcorenomad.ModInfo;
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

/**
 * Created by FusionLord on 4/25/2016.
 */
public class ModBlocks
{
	public final static BlockAnvil anvil = new BlockAnvil();
	public final static BlockBackpack backpack = new BlockBackpack();

	public static void registerBlocks()
	{
		for (Field field : ModBlocks.class.getFields())
		{
			try
			{
				if (BlockUpgradable.class.isInstance(field.get(ModBlocks.class)))
				{
					BlockUpgradable block = (BlockUpgradable) field.get(ModBlocks.class);
					register(block, new ItemBlockUpgradable(block));
				}
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void register(Block block)
	{
		register(block, new ItemBlock(block));
	}

	private static void register(Block block, Item itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
		if (block instanceof ITileEntityProvider)
		{
			GameRegistry.registerTileEntity(((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass(), "tileentity." + block.getRegistryName());
		}
		LogHelper.info("Registering block object: " + block.getRegistryName());
	}

	public static void registerRenders()
	{
		for (Field field : ModBlocks.class.getFields())
		{
			try
			{
				if (BlockUpgradable.class.isInstance(field.get(ModBlocks.class)))
				{
					BlockUpgradable block = (BlockUpgradable) field.get(ModBlocks.class);
					for (EnumUpgrade upgrade : block.getValidLevels())
					{
						LogHelper.info("Registering inventory renderer: " + block.getRegistryName());
						ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), upgrade.ordinal(), new ModelResourceLocation(block.getRegistryName(), "level=" + upgrade.getName()));
					}
				}
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
