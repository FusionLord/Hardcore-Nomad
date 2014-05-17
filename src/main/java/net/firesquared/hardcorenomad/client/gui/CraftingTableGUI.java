package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.CraftingTableContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.world.World;

public class CraftingTableGUI extends GuiCrafting
{
	public CraftingTableGUI(EntityPlayer entityPlayer, World world, int x, int y, int z)
	{
		super(entityPlayer.inventory, world, x, y, z);
	}


}
