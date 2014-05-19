package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNomadBase extends Item
{
	public static int getTierLevel(ItemStack itemStack)
	{
		return itemStack.getTagCompound().getInteger(NBTHelper.NBT_TIER);
	}

}
