package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.item.upgrades.itemUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BackPackInventory extends NBTBackedInventory
{

	public BackPackInventory(NBTTagCompound backingTag)
	{
		super(backingTag);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		if(slot < 0 || slot >= getSizeInventory())
			return false;
		else if(slot == 0)
			return is.getItem() instanceof itemUpgrade;
		else if(slot == 1)
			return false;//campfire
		else if(slot == 2)
			return false;//bed
		else if(slot == 3)
			return false;//crafting
		else if(slot == 4)
			return false;//storage
		else if(slot == 5)
			return false;//enchanting
		else if(slot == 6)
			return false;//anvil
		else if(slot == 7)
			return false;//cobblegen
		else if(slot == 8)
			return false;//brewing
		else if(slot == 9)
			return false;//fluids
		else
			return true;
	}
	
}
