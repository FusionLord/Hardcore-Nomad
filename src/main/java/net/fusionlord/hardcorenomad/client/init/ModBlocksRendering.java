package net.fusionlord.hardcorenomad.client.init;

import com.google.common.base.Joiner;
import net.fusionlord.hardcorenomad.common.blocks.BlockUpgradable;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.init.ModBlocks;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModBlocksRendering
{
	public static void registerTESRS()
	{
		ModBlocks.getBlocks().stream().filter(block -> block instanceof BlockUpgradable).forEach(block -> {
			BlockUpgradable blockUpgradable = (BlockUpgradable) block;
			if(blockUpgradable.getRender() != null)
			{
				Class tileEntityClass = blockUpgradable.getTileEntityClass();
				ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, blockUpgradable.getRender());
				LogHelper.debug("Registering TESR for: " + blockUpgradable + " as " + TileEntityRendererDispatcher.instance.getSpecialRendererByClass(tileEntityClass).getClass().getName());
			}
			else
			{
				LogHelper.debug("Failed to register TESR for: " + blockUpgradable);
			}
		});
	}

	public static void registerRenders()
	{
		for (Block block : ModBlocks.getBlocks())
		{
			if (block instanceof BlockUpgradable)
			{
				BlockUpgradable blockUpgradable = (BlockUpgradable) block;
				for(EnumUpgrade upgrade : blockUpgradable.getValidLevels())
				{
					IBlockState state = block.getDefaultState()
							.withProperty(BlockUpgradable.LEVEL, upgrade)
							.withProperty(BlockUpgradable.FACING, EnumFacing.SOUTH);
					LogHelper.debug(">>>> Item Render state: " + state);
					regRender(block, upgrade.ordinal(), block.getRegistryName().toString().replace(":", ":items/"), blockstateToVariant(state));
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

	private static void regRender(Block block)
	{
		regRender(block, 0);
	}

	private static void regRender(Block block, int meta)
	{
		regRender(block, meta, "inventory");
	}

	private static void regRender(Block block, int meta, String variant)
	{
		regRender(block, meta, block.getRegistryName(), variant);
	}

	private static void regRender(Block block, int meta, ResourceLocation file, String variant)
	{
		regRender(block, meta, file.toString(), variant);
	}

	private static void regRender(Block block, int meta, String file, String variant)
	{
		regRender(block, meta, new ModelResourceLocation(file, variant));
	}

	private static void regRender(Block block, int meta, ModelResourceLocation modelResourceLocation)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	private static String blockstateToVariant(IBlockState state)
	{
		return Joiner.on(',').withKeyValueSeparator("=").join(state.getProperties().entrySet().stream().map(e -> Pair.of(e.getKey().getName(), e.getValue())).sorted(Map.Entry.comparingByKey()).iterator()).toLowerCase();
	}

}
