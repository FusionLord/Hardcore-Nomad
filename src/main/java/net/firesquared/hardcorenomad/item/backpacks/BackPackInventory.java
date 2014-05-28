package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.tile.BackpackInvWrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class BackPackInventory implements IInventory
{
	//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
	//get the value from where it is defined.  Don't just put the current value in as an integer
	protected BackpackInvWrapper inv;
	protected NBTTagCompound tag;
	protected ItemStack itemStack;

	public BackPackInventory(ItemStack itemStack)
	{
		this.itemStack = itemStack;
		tag = itemStack.stackTagCompound;
		inv = new BackpackInvWrapper();
		BackpackInvWrapper.readExtraNBT(tag, inv);
	}

	@Override
	public void openInventory()
	{inv.openInventory();}

	@Override
	public void closeInventory()
	{inv.closeInventory();}
	
	public BackPackType getBackpackType()
	{
		return inv.type;
	}

	@Override
	public int getSizeInventory()
	{
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inv.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int value)
	{
		return inv.decrStackSize(slot, value);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return inv.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack is)
	{
		inv.setInventorySlotContents(slot, is);
	}

	@Override
	public String getInventoryName()
	{
		return inv.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return inv.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inv.getInventoryStackLimit();
	}

	@Override
	public void markDirty()
	{
		inv.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inv.isUseableByPlayer(player);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		return inv.isItemValidForSlot(slot, is);
	}
	
	public void writeExtraNBT(NBTTagCompound tag)
	{
		BackpackInvWrapper.writeExtraNBT(tag, inv);
	}
}