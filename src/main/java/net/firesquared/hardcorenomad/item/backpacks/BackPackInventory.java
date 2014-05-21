package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.block.campcomponents.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class BackPackInventory implements IInventory
{
	protected ItemStack[] storageInventory;
	protected ItemStack[] componentInventory = new ItemStack[9];
	protected ItemStack upgradeSlot;
	protected ItemStack armorSlot;
	protected BackPackType currentLevel;
	protected NBTTagCompound tag;
	protected ItemStack itemStack;

	public BackPackInventory(ItemStack itemStack)
	{
		this.itemStack = itemStack;
		tag = itemStack.stackTagCompound;
		currentLevel = BackPackType.values()[Math.max(0, 
				Math.min(tag.getInteger(NBTHelper.CURRENTLEVEL), 
						BackPackType.values().length-1))];
		readExtraNBT(tag);
	}

	public void writeExtraNBT(NBTTagCompound tag)
	{
		tag.setInteger(NBTHelper.CURRENTLEVEL, currentLevel.ordinal());
		
		NBTTagCompound comInvTag = new NBTTagCompound();
		for (int i = 0; i < componentInventory.length; i++)
		{
			if(componentInventory[i]!=null)
				comInvTag.setTag(NBTHelper.SLOT.concat(""+i), componentInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.COMINV, comInvTag);
		
		NBTTagCompound stgInvTag = new NBTTagCompound();
		for (int i = 0; i < storageInventory.length; i++)
		{
			if(storageInventory[i]!=null)
				stgInvTag.setTag(NBTHelper.SLOT.concat(""+i), storageInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.STGINV, stgInvTag);
		
		if(upgradeSlot != null)
			tag.setTag(NBTHelper.UPGRADESLOT, upgradeSlot.writeToNBT(new NBTTagCompound()));
		if(armorSlot != null && currentLevel.hasArmorSlot())
			tag.setTag(NBTHelper.ARMORSLOT, armorSlot.writeToNBT(new NBTTagCompound()));
	}

	public void readExtraNBT(NBTTagCompound tag)
	{
		NBTTagCompound comInvTag = tag.getCompoundTag(NBTHelper.COMINV);
		for (int i = 0; i < 9; i++)
		{
			if(comInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				componentInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		storageInventory = new ItemStack[currentLevel.getStorageCount()];
		for (int i = 0; i < storageInventory.length; i++)
		{
			if(stgInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				storageInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		if(tag.hasKey(NBTHelper.UPGRADESLOT))
			upgradeSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.UPGRADESLOT));
		if(currentLevel.hasArmorSlot() && tag.hasKey(NBTHelper.ARMORSLOT))
			armorSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
	}

	@Override
	public int getSizeInventory()
	{
		return currentLevel.getStorageCount();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		//TODO: change so that the last 3 levels can all mount some form of armor
		//improved limited to un-enchanted iron or leather armor only; possibly also wood armor from TC
		//advanced can have any kind of un-enchanted, vanilla armor
		//aromored has no restrictions, beyond that the item extend ItemArmor
		boolean isArmor = currentLevel.hasArmorSlot();

		if (slot < storageInventory.length)
			return storageInventory[slot];
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			return componentInventory[slot - storageInventory.length];
		if (slot == storageInventory.length + componentInventory.length)
			return upgradeSlot;
		if (slot == storageInventory.length + componentInventory.length + 1 && isArmor)
			return armorSlot;
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int value)
	{
		ItemStack isTemp = getStackInSlot(slot);
		if(isTemp == null)
			return null;
		if(isTemp.stackSize > value)
		{
			isTemp.stackSize -= value;
			setInventorySlotContents(slot, isTemp.copy());
			isTemp.stackSize = value;
			return isTemp;
		}
		else
		{
			setInventorySlotContents(slot, null);
			return isTemp;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		boolean isArmor = currentLevel.hasArmorSlot();
		if(itemStack != null)
			itemStack = itemStack.copy();
		
		if (slot < storageInventory.length)
			storageInventory[slot] = itemStack;
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			componentInventory[slot - storageInventory.length] = itemStack;
		if (slot == storageInventory.length + componentInventory.length)
			upgradeSlot = itemStack;
		if (slot == storageInventory.length + componentInventory.length + 1 && isArmor)
			armorSlot = itemStack;
	}

	@Override
	public String getInventoryName()
	{
		return StatCollector.translateToLocal("inventory.backpack.name");
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false; // TODO: add ability to change the backpack's name in the anvil
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		writeExtraNBT(tag);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		if(itemStack == null)
			return true;
		if (slot < storageInventory.length)
			return !(itemStack.getItem() instanceof ItemBackPack);
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			return Block.getBlockFromItem(itemStack.getItem()) instanceof BlockCampComponent;
		if (slot == storageInventory.length + componentInventory.length)
			return itemStack.getItem() instanceof ItemUpgrade;
		if (slot == storageInventory.length + componentInventory.length + 1)
			return currentLevel.hasArmorSlot();
		return false;
	}

	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}
	
	public BackPackType getBackpackType()
	{
		return currentLevel;
	}
}