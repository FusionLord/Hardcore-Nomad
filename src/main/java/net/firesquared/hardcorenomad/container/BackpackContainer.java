package net.firesquared.hardcorenomad.container;

import codechicken.core.inventory.SlotDummy;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.backpacks.BackPackInventory;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BackpackContainer extends Container
{
	public final boolean isPlaced;
	public final boolean isArmor;
	public IInventory backPack;

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}

	public BackpackContainer(InventoryPlayer invPlayer, TileEntityBackPack backPack)
	{
		this.backPack = backPack;
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
		isArmor = backPack.getCurrentLevel() == BackPackType.BACKPACK_ARMORED.ordinal();
		isPlaced = true;
	}

	public BackpackContainer(InventoryPlayer invPlayer, ItemStack currentItem)
	{
		backPack = new BackPackInventory(currentItem);
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
		isArmor = currentItem.getTagCompound().getInteger(NBTHelper.CURRENTLEVEL) == BackPackType.BACKPACK_ARMORED.ordinal();
		isPlaced = false;
	}

	private void bindPlayerSlots(InventoryPlayer invPlayer)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, j * 18 + 8, i * 18 + 18 * 4 + 3));
		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(invPlayer, i, i * 18 + 8, 18 * 7 + 7));
	}

	private void bindBackpackSlots()
	{
		int paddingLeft = 174;
		int paddingTop = 12;
		int slot;
		for (slot = 0; slot < backPack.getSizeInventory(); slot++)
		{
			LogHelper.debug("[Binding slots] - storageslot " + slot);
			addSlotToContainer(new Slot(backPack, slot, paddingLeft + ((slot % 2) * 18), paddingTop + ((slot / 2) * 18)));
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new SlotDummy(backPack, slot, 8 + (i * 18), 48));
			slot++;
		}

		addSlotToContainer(new Slot(backPack, slot, 128, 4));
		slot++;
		if (isArmor)
		{
			addSlotToContainer(new Slot(backPack, slot, 128, 4));
		}
	}

}
