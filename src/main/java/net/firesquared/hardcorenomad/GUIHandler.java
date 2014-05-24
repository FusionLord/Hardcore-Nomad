package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.network.IGuiHandler;
import net.firesquared.hardcorenomad.client.gui.*;
import net.firesquared.hardcorenomad.container.*;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
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
				TileEntityBackPack backpack = Tiles.<TileEntityBackPack>getTileEntity(world, x, y, z);
				return new BackpackContainer(player.inventory, backpack);
			case 1:
				return new BackpackContainer(player.inventory, player.inventory.getCurrentItem());
			case 2:
				return new CampFireContainer(player.inventory, (TileEntityCampFire) world.getTileEntity(x, y, z));
			case 3:
				return new CraftingTableContainer(player.inventory, world, x, y, z);
			case 4:
				return new EnchantmentContainer(player.inventory, world, x, y, z);
			case 5:
				return new AnvilContainer(player.inventory, world, x, y, z, player);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Helper.getLogger().debug("Opening GUI #" + ID);
		switch(ID)
		{
			case 0:
			case 1:
				return new BackpackGUI((BackpackContainer) getServerGuiElement(ID, player, world, x, y, z));
			case 2:
				return new CampFireGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
			case 3:
				return new CraftingTableGUI(player, world, x, y, z);
			case 4:
				return new EnchantmentGUI(player.inventory, world, x, y, z, null);
			case 5:
				return new AnvilGUI(player.inventory, world, x, y, z);
			default:
				return null;
		}
	}

}
