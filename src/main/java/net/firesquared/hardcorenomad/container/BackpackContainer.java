package net.firesquared.hardcorenomad.container;

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
	public IInventory backPack;
	public boolean isPlaced;

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
		isPlaced = true;
	}

	public BackpackContainer(InventoryPlayer invPlayer, ItemStack currentItem)
	{
		this.backPack = new BackPackInventory(currentItem.stackTagCompound);
		bindBackpackSlots();
		bindPlayerSlots(invPlayer);
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
		int i = 0;
		switch(backPack.getSizeInventory())
		{
			case 6+9:
				for(int w = 0; w < 2; w++)
					for(int h = 0; h < 3; h++)
						addSlotToContainer(new Slot(backPack, i++, w * 18 + 192, h * 18 + 6));
				break;
			case 12+9:
				for(int w = 0; w < 3; w++)
					for(int h = 0; h < 4; h++)
						addSlotToContainer(new Slot(backPack, i++, w * 18 + 192, h * 18 + 6));
				break;
			case 21+9:
				for(int w = 0; w < 3; w++)
					for(int h = 0; h < 7; h++)
						addSlotToContainer(new Slot(backPack, i++, w * 18 + 192, h * 18 + 6));
				break;
			case 32+9:
			default:
				for(int w = 0; w < 4; w++)
					for(int h = 0; h < 8; h++)
						addSlotToContainer(new Slot(backPack, i++, w * 18 + 192, h * 18 + 6));
				break;
		}
		addSlotToContainer(new Slot(backPack, i + 9, 128, 4));
	}

}
