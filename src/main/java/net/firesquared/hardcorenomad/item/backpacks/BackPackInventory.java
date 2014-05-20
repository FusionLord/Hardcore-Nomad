package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class BackPackInventory implements IInventory
{
	protected ItemStack[] storageInventory;
	protected ItemStack[] componentInventory = new ItemStack[9];
	protected ItemStack upgradeSlot;
	protected ItemStack armorSlot;
	protected int currentLevel;
	protected NBTTagCompound tag;
	protected ItemStack itemStack;

	public BackPackInventory(ItemStack itemStack)
	{
		this.itemStack = itemStack;
		tag = itemStack.getTagCompound();
		readExtraNBT(tag);
	}

	public void writeExtraNBT(NBTTagCompound tag)
	{
		NBTTagCompound comInvTag = new NBTTagCompound();
		for (int i = 0; i < 9; i++)
		{
			comInvTag.setTag(NBTHelper.SLOT.concat(""+i), componentInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.COMINV, comInvTag);
		NBTTagCompound stgInvTag = new NBTTagCompound();
		int stgInvSize = storageInventory.length;
		stgInvTag.setInteger(NBTHelper.STGINVSIZE, stgInvSize);
		for (int i = 0; i < stgInvSize; i++)
		{
			stgInvTag.setTag(NBTHelper.SLOT.concat(""+i), storageInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.STGINV, stgInvTag);
		tag.setTag(NBTHelper.UPGRADESLOT, upgradeSlot.writeToNBT(new NBTTagCompound()));
		tag.setTag(NBTHelper.ARMORSLOT, armorSlot.writeToNBT(new NBTTagCompound()));
	}

	public void readExtraNBT(NBTTagCompound tag)
	{
		NBTTagCompound comInvTag = tag.getCompoundTag(NBTHelper.COMINV);
		for (int i = 0; i < 9; i++)
		{
			componentInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		int stgInvSize = stgInvTag.getInteger(NBTHelper.STGINVSIZE);
		storageInventory = new ItemStack[stgInvSize];
		for (int i = 0; i < stgInvSize; i++)
		{
			storageInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		upgradeSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.UPGRADESLOT));
		armorSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
	}

	@Override
	public int getSizeInventory()
	{
		return 9 * (currentLevel + 1);
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		boolean isArmor = currentLevel == BackPackType.BACKPACK_ARMORED.ordinal();

		if (slot < storageInventory.length)
			return storageInventory[slot];

		if (slot > storageInventory.length && slot < storageInventory.length + 9)
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
		ItemStack itemStack = getStackInSlot(slot);
		itemStack.stackSize -= value;
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		if (slot < storageInventory.length)
			storageInventory[slot] = itemStack;
		if (slot == storageInventory.length)
			upgradeSlot = itemStack;
		if (slot == storageInventory.length + 1 && currentLevel == 4)
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
		if (slot < storageInventory.length)
			return true;
		if (slot == storageInventory.length && itemStack.getItem() instanceof ItemUpgrade)
			return true;
		if (slot == storageInventory.length + 1 && itemStack.getItem() instanceof ItemArmor && !(itemStack.getItem() instanceof ItemBackPack))
			return true;
		return false;
	}

	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}
}