package net.firesquared.hardcorenomad.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.world.World;

public class AnvilContainer extends ContainerRepair
{
	public AnvilContainer(InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5, EntityPlayer par6EntityPlayer)
	{
		super(par1InventoryPlayer, par2World, par3, par4, par5, par6EntityPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
}
