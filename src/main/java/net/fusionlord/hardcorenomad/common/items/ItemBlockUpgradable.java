package net.fusionlord.hardcorenomad.common.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.fusionlord.hardcorenomad.common.blocks.BlockUpgradable;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Created by FusionLord on 4/26/2016.
 */
public class ItemBlockUpgradable extends ItemBlock
{
	public ItemBlockUpgradable(Block block)
	{
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ModCreativeTab.INSTANCE);
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
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (EnumUpgrade upgrade : ((BlockUpgradable)block).getValidLevels())
		{
			subItems.add(new ItemStack(this, 1, upgrade.ordinal()));
		}
	}
}
