package net.firesquared.hardcorenomad.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class CampFireContainer extends Container
{
	public TileEntityCampFire tileEntityCampFire;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public CampFireContainer(InventoryPlayer inventoryPlayer, TileEntityCampFire tileEntityCampFire) {
		this.tileEntityCampFire = tileEntityCampFire;
		this.addSlotToContainer(new Slot(tileEntityCampFire, 0, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityCampFire, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tileEntityCampFire, 2, 116, 35));
		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tileEntityCampFire.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tileEntityCampFire.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.tileEntityCampFire.currentItemBurnTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if (this.lastCookTime != this.tileEntityCampFire.furnaceCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntityCampFire.furnaceCookTime);
			}

			if (this.lastBurnTime != this.tileEntityCampFire.furnaceBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntityCampFire.furnaceBurnTime);
			}

			if (this.lastItemBurnTime != this.tileEntityCampFire.currentItemBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntityCampFire.currentItemBurnTime);
			}
		}

		this.lastCookTime = this.tileEntityCampFire.furnaceCookTime;
		this.lastBurnTime = this.tileEntityCampFire.furnaceBurnTime;
		this.lastItemBurnTime = this.tileEntityCampFire.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			this.tileEntityCampFire.furnaceCookTime = par2;
		}

		if (par1 == 1)
		{
			this.tileEntityCampFire.furnaceBurnTime = par2;
		}

		if (par1 == 2)
		{
			this.tileEntityCampFire.currentItemBurnTime = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return this.tileEntityCampFire.isUseableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
				else if (TileEntityFurnace.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return null;
					}
				}
				else if (par2 >= 3 && par2 < 30)
					{
						if (!this.mergeItemStack(itemstack1, 30, 39, false))
						{
							return null;
						}
					}
					else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
						{
							return null;
						}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
				{
					return null;
				}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}
