package net.fusionlord.hardcorenomad.common.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.fusionlord.hardcorenomad.common.blocks.BlockBackpack;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemBlockUpgradable extends ItemBlock
{
	public ItemBlockUpgradable(Block block)
	{
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setRegistryName(block.getRegistryName());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			tooltip.add(ChatFormatting.DARK_AQUA + String.format("%s: %s", I18n.translateToLocal("string.tier"), I18n.translateToLocal("tier." + EnumUpgrade.values()[stack.getMetadata()].getName())));
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		// Block should ONLY be placed in code, or in creative, or if it is a BackPack.
		return playerIn.capabilities.isCreativeMode || block instanceof BlockBackpack ? super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ) : EnumActionResult.SUCCESS;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		block.getSubBlocks(itemIn, tab, subItems);
	}
}
