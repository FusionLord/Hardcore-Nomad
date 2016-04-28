package net.fusionlord.hardcorenomad.common.init;

import net.fusionlord.hardcorenomad.common.blocks.*;
import net.fusionlord.hardcorenomad.common.items.ItemBlockUpgradable;
import net.fusionlord.hardcorenomad.util.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FusionLord on 4/25/2016.
 */
public class ModBlocks
{
	private static List<Block> blockList;

	public final static BlockAnvil anvil = new BlockAnvil();
	public final static BlockBackpack backpack = new BlockBackpack();
	public final static BlockBedroll bedroll = new BlockBedroll();
//	public final static BlockCampFire campfire = new BlockCampFire();
//	public final static BlockCobbleGenerator cobbleGen = new BlockCobbleGenerator();
//	public final static BlockCrafter crafter = new BlockCrafter();
	public final static BlockEnchantingTable enchanter = new BlockEnchantingTable();

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
		LogHelper.info("Registering block object: " + block.getRegistryName());
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		if (block instanceof ITileEntityProvider)
		{
			BlockUpgradable blockUpgradable = (BlockUpgradable) block;
			Class tileEntityClass = blockUpgradable.getTileEntityClass();
			GameRegistry.registerTileEntity(tileEntityClass, "tileentity." + block.getRegistryName());
			LogHelper.info("Registering TE object for: " + blockUpgradable + " as " + tileEntityClass.getName());
		}
	}

	public static List<Block> getBlocks()
	{
		if (blockList != null) return blockList;
		blockList = new ArrayList<>();
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
