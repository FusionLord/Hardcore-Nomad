package net.firesquared.hardcorenomad.container;

import java.util.ArrayList;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
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
	public final ArrayList<Slot> upgradeDisplaySlots = new ArrayList<Slot>();
	boolean isServer;

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
		isServer = !invPlayer.player.worldObj.isRemote;
		this.backPack = backPack;
		isPlaced = true;
		type = backPack.getType();
		
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
	}

	public BackpackContainer(InventoryPlayer invPlayer, ItemStack currentItem)
	{
		assert(invPlayer != null && currentItem != null);
		if(currentItem.stackTagCompound == null)
			Helper.getNomadLogger().fatal("null input to backpack container");
		isServer = !invPlayer.player.worldObj.isRemote;
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
			if(isPlaced || i!=meSlot)
				addSlotToContainer(new Slot(invPlayer, i, i * 18 + 8, 18 * 7 + 7));
	}

	private void bindBackpackSlots()
	{
		int paddingLeft = 174;
		int paddingTop = 7;
		int slot = 0;
		for (int x = 0; x < type.storageW; x++)
			for(int y = 0; y < type.storageH; y++)
				addSlotToContainer(new Slot(backPack, slot++, paddingLeft + x * 18, paddingTop + y * 18)
				{@Override
					public boolean isItemValid(ItemStack is)
					{
						return backPack.isItemValidForSlot(this.slotNumber, is);
					}
				});
		
		Slot tempSlot;
		for (int i = 0; i < ItemUpgrade.getCampComponentCount(); i++)
		{
			tempSlot = new Slot(backPack, slot++, 8 + i * 18, 50)
			{@Override
				public boolean isItemValid(ItemStack is)
				{
					return backPack.isItemValidForSlot(this.slotNumber, is);
				}
			};
			upgradeDisplaySlots.add(tempSlot);
			addSlotToContainer(tempSlot);
		}

		addSlotToContainer(
				new Slot(backPack, slot++, 8 + 7 * 18, 8)			
				{@Override
					public boolean isItemValid(ItemStack is)
					{
						return backPack.isItemValidForSlot(this.slotNumber, is);
					}
				});
		if (type.hasArmorSlot)
			addSlotToContainer(new Slot(backPack, slot++, 8 + 8 * 18, 8)
			{@Override
				public boolean isItemValid(ItemStack is)
				{
					return backPack.isItemValidForSlot(this.slotNumber, is);
				}
			});
		Helper.getNomadLogger().info((isServer?"Server":"Client")+" has " + slot + " slots");
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
