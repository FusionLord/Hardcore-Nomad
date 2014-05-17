package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.network.IGuiHandler;
import net.firesquared.hardcorenomad.client.gui.BackpackGUI;
import net.firesquared.hardcorenomad.client.gui.CampFireGUI;
import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.container.CampFireContainer;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case 0:
				return new BackpackContainer(player.inventory, (TileEntityBackPack) world.getTileEntity(x, y, z));
			case 1:
				return new BackpackContainer(player.inventory, player.inventory.getCurrentItem());
			case 2:
				return new CampFireContainer(player.inventory, (TileEntityCampFire) world.getTileEntity(x, y, z));

			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case 0:
			case 1:
				return new BackpackGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
			case 2:
				return new CampFireGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
			default:
				return null;
		}
	}

}
