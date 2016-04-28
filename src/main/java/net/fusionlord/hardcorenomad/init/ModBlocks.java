package net.fusionlord.hardcorenomad.init;

import com.google.common.base.Joiner;

import net.fusionlord.hardcorenomad.common.blocks.*;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.items.ItemBlockUpgradable;
import net.fusionlord.hardcorenomad.util.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by FusionLord on 4/25/2016.
 */
public class ModBlocks
{
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

	@SideOnly(Side.CLIENT)
	public static void registerTESRS()
	{
		getBlocks().stream().filter(block -> block instanceof BlockUpgradable).forEach(block -> {
			BlockUpgradable blockUpgradable = (BlockUpgradable) block;
			if(blockUpgradable.getRender() != null)
			{
				Class tileEntityClass = blockUpgradable.getTileEntityClass();
				ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, blockUpgradable.getRender());
				LogHelper.info("Registering TESR for: " + blockUpgradable + " as " + TileEntityRendererDispatcher.instance.getSpecialRendererByClass(tileEntityClass).getClass().getName());
				((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(blockUpgradable.getRender());
			}
			else
			{
				LogHelper.info("Failed to register TESR for: " + blockUpgradable);
			}
		});
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		for (Block block : getBlocks())
		{
			if (block instanceof BlockUpgradable)
			{
				BlockUpgradable blockUpgradable = (BlockUpgradable) block;
				for(EnumUpgrade upgrade : blockUpgradable.getValidLevels())
				{
					regRender(block, upgrade.ordinal(), blockstateToVariant(block.getDefaultState().withProperty(BlockUpgradable.LEVEL, upgrade).withProperty(BlockUpgradable.FACING, EnumFacing.SOUTH)));
				}
			}
			else
			{
				List<ItemStack> list = new ArrayList<>();
				block.getSubBlocks(Item.getItemFromBlock(block), block.getCreativeTabToDisplayOn(), list);
				if (list.size() > 1)
				{
					for (ItemStack stack : list)
					{
						regRender(block, stack.getMetadata());
					}
				}
				else
				{
					regRender(block);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static void regRender(Block block)
	{
		regRender(block, 0);
	}

	@SideOnly(Side.CLIENT)
	private static void regRender(Block block, int meta)
	{
		regRender(block, meta, "inventory");
	}

	@SideOnly(Side.CLIENT)
	private static void regRender(Block block, int meta, String variant)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
	}

	private static List<Block> getBlocks()
	{
		List<Block> blockList = new ArrayList<>();
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

	@SideOnly(Side.CLIENT)
	private static String blockstateToVariant(IBlockState state)
	{
		return Joiner.on(',').withKeyValueSeparator("=").join(state.getProperties().entrySet().stream().map(e -> Pair.of(e.getKey().getName(), e.getValue())).sorted(Map.Entry.comparingByKey()).iterator()).toLowerCase();
	}
}
