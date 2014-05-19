package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNomadBase extends Item
{
	public static int getTierLevel(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
			return 0;
		return itemStack.getTagCompound().getInteger(NBTHelper.NBT_TIER);
	}

}
