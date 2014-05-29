package net.firesquared.hardcorenomad.container;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.Random;

public class EnchantmentContainer extends ContainerEnchantment
{
	private Random rand = new Random();
	private World worldPointer;

	public EnchantmentContainer(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
		super (par1InventoryPlayer, par2World, par3, par4, par5);
		this.worldPointer = par2World;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if (par1IInventory == this.tableInventory)
		{
			ItemStack itemstack = par1IInventory.getStackInSlot(0);
			int i;

			if (itemstack != null && itemstack.isItemEnchantable())
			{
				this.nameSeed = this.rand.nextLong();

				if (!this.worldPointer.isRemote)
				{
					i = 0;
					int j;
					float power = 15;

					for (j = 0; j < 3; ++j)
					{
						this.enchantLevels[j] = EnchantmentHelper.calcItemStackEnchantability(this.rand, j, (int)power, itemstack);
					}

					this.detectAndSendChanges();
				}
			}
			else
			{
				for (i = 0; i < 3; ++i)
				{
					this.enchantLevels[i] = 0;
				}
			}
		}
	}
}
