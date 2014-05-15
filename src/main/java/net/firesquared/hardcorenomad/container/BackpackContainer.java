package net.firesquared.hardcorenomad.container;

import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BackpackContainer extends Container
{
	public TileEntityBackPack backPack;
	
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
	}

	private void bindPlayerSlots(InventoryPlayer invPlayer)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, j * 18, i * 18));
		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(invPlayer, i, i * 18, 0));
	}

	private void bindBackpackSlots()
	{
		
	}
	
}
