package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.network.IGuiHandler;
import net.firesquared.hardcorenomad.client.gui.BackpackGUI;
import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.entity.player.EntityPlayer;
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
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new BackpackGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
	}

}
