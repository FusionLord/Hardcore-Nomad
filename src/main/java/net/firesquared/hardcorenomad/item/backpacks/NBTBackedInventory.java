package net.firesquared.hardcorenomad.item.backpacks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTBackedInventory implements IInventory
{
	NBTTagCompound backingTag;
	
	public static NBTTagCompound createNBTInventory(int size, int maxStack)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("size", (short) size);
		tag.setByte("maxStack", (byte) maxStack);
		NBTTagCompound inv = new NBTTagCompound();
		tag.setTag("inv", inv);
		return tag;
	}
	
	public static NBTTagCompound createNBTInventory(int size, int maxStack, String name)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("size", (short) size);
		tag.setByte("maxStack", (byte) maxStack);
		NBTTagCompound inv = new NBTTagCompound();
		tag.setTag("inv", inv);
		tag.setString("name", name);
		return tag;
	}
	
	public NBTBackedInventory(NBTTagCompound backingTag)
	{
		this.backingTag = backingTag;
	}
	
	public NBTBackedInventory(int size, int maxStack)
	{
		backingTag = createNBTInventory(size, maxStack);
	}
	
	@Override
	public int getSizeInventory()
	{
		return backingTag.getShort("size");
	}
	
	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return ItemStack.loadItemStackFromNBT(backingTag.getCompoundTag("inv").getCompoundTag(String.valueOf(var1)));
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		NBTTagCompound inv = backingTag.getCompoundTag("inv");
		ItemStack isTemp = ItemStack.loadItemStackFromNBT(inv.getCompoundTag(String.valueOf(slot)));
		if(isTemp == null)
			return null;
		if(isTemp.stackSize > count)
		{
			isTemp.stackSize -= count;
			isTemp.writeToNBT(inv.getCompoundTag(String.valueOf(slot)));
			isTemp.stackSize = count;
			return isTemp.copy();
		}
		inv.removeTag(String.valueOf(slot));
		return isTemp;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack is)
	{
		NBTTagCompound inv = backingTag.getCompoundTag("inv");
		NBTTagCompound temp = new NBTTagCompound();
		if(is!=null)
		{
			is.writeToNBT(temp);
			inv.setTag(String.valueOf(slot), temp);
		}
		else
		{
			inv.removeTag(String.valueOf(slot));
		}
	}
	
	@Override
	public String getInventoryName()
	{
		if(!backingTag.hasKey("name"))
			return null;
		return backingTag.getString("name");
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return backingTag.hasKey("name");
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return backingTag.getByte("maxStack");
	}
	
	@Override
	public void markDirty()
	{
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}
	
	@Override
	public void openInventory()
	{	}
	
	@Override
	public void closeInventory()
	{	}
	
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		return true;
	}
	
}
