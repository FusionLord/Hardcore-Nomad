package net.firesquared.hardcorenomad.container;

import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
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
	public final BackPackType type;
	public IInventory backPack;
	ItemStack me;
	int meSlot = -1;

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
		super();
		this.backPack = backPack;
		isPlaced = true;
		type = backPack.getType();
		
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
	}

	public BackpackContainer(InventoryPlayer invPlayer, ItemStack currentItem)
	{
		if(invPlayer == null || currentItem == null || currentItem.stackTagCompound == null)
		{
			LogHelper.fatal("null input to backpack container");
		}
		me = currentItem;
		meSlot = invPlayer.currentItem;
		isPlaced = false;
		BackPackInventory bpi = new BackPackInventory(currentItem);
		type = bpi.getBackpackType();
		backPack = bpi;
		
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
	}

	private void bindPlayerSlots(InventoryPlayer invPlayer)
	{
		for(int i = 1; i < 4; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(invPlayer, j + i * 9, j * 18 + 8, (i+3) * 18+ 3));
		for(int i = 0; i < 9; i++)
//			if(isPlaced || i!=meSlot)
				addSlotToContainer(new Slot(invPlayer, i, i * 18 + 8, 18 * 7 + 7));
//			else
//				addSlotToContainer(new Slot(invPlayer, i, -1000, -1000));
	}

	private void bindBackpackSlots()
	{
		int paddingLeft = 174;
		int paddingTop = 7;
		int slot = 0;
		for (int x = 0; x < type.getStorageWidth(); x++)
			for(int y = 0; y < type.getStorageHeight(); y++)
				addSlotToContainer(new Slot(backPack, slot++, paddingLeft + x * 18, paddingTop + y * 18));
		
//		slot += 9;
		for (int i = 0; i < 8; i++)
		{
			addSlotToContainer(new Slot(backPack, slot++, 8 + i * 18, 50));
		}

		addSlotToContainer(new Slot(backPack, slot++, 8 + 8 * 18, 50));
		if (type.hasArmorSlot())
			addSlotToContainer(new Slot(backPack, slot++, 8 + 8 * 18, 8));
	}
	
	public ItemStack getThisBackpack()
	{
		return me;
	}
	
	public int getMySlot()
	{
		return meSlot;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer entityPlayer)
	{
		if(!isPlaced && !entityPlayer.worldObj.isRemote)
		{
			BackPackInventory bpi = (BackPackInventory) backPack;
			ItemStack is = entityPlayer.inventory.getStackInSlot(meSlot);
			bpi.writeExtraNBT(is.stackTagCompound);
			entityPlayer.inventory.setInventorySlotContents(meSlot, is);
			entityPlayer.inventory.inventoryChanged = true;
		}
		super.onContainerClosed(entityPlayer);
	}

}
