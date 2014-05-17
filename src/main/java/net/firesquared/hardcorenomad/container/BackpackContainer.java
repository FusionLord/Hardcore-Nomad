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
			addSlotToContainer(new Slot(invPlayer, i, i * 18 + 8, 18 * 7 + 3));
	}

	private void bindBackpackSlots()
	{

	}

}
