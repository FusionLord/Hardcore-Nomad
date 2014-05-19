package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.block.IBlockCampComponent;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.minecraft.block.Block;
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
		int div = backingTag.getInteger("divider");
		if(slot < 0)
			if(slot == -1)
				return is.getItem() instanceof ItemUpgrade;
			else
				return false;
		else if(slot >= getSizeInventory())
				return false;
		else
			if(slot<backingTag.getInteger("div"))
				return true;
			else
				return Block.getBlockFromItem(is.getItem()) instanceof IBlockCampComponent;
	}
	
}
