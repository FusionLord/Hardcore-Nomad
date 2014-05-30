package net.firesquared.hardcorenomad;

import net.firesquared.hardcorenomad.client.gui.*;
import net.firesquared.hardcorenomad.container.*;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBrewingStand;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{
	public enum GUIType
	{
		BACKPACK_TILEENTITY(0),
		BACKPACK_ITEMFORM(10),
		CAMPFIRE_TILEENTITY(20),
		ENCHANTMENT_BLOCK(30),
		BREWING_STAND_BLOCK(40);
		public final int ID;
		private GUIType(int ID)
		{
			this.ID = ID;
		}
		public static GUIType fromID(int ID)
		{
			for(GUIType t : GUIType.values())
				if(t.ID == ID)
					return t;
			return null;
		}
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(GUIType.fromID(ID))
		{
			case BACKPACK_TILEENTITY:
				TileEntityBackPack backpack = Tiles.<TileEntityBackPack>getTileEntity(world, x, y, z);
				return new BackpackContainer(player.inventory, backpack);
			case BACKPACK_ITEMFORM:
				return new BackpackContainer(player.inventory, player.inventory.getCurrentItem());
			case CAMPFIRE_TILEENTITY:
				return new CampFireContainer(player.inventory, (TileEntityCampFire) world.getTileEntity(x, y, z));
			case ENCHANTMENT_BLOCK:
				return new EnchantmentContainer(player.inventory, world, x, y, z);
			case BREWING_STAND_BLOCK:
				TileEntityBrewingStand brewingStand = Tiles.<TileEntityBrewingStand>getTileEntity(world, x, y, z);
				if(brewingStand==null)
					return null;
				return new BrewingContainer(player.inventory, brewingStand);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Helper.getNomadLogger().debug("Opening GUI #" + ID);
		switch(GUIType.fromID(ID))
		{
			case BACKPACK_TILEENTITY:
			case BACKPACK_ITEMFORM:
				return new BackpackGUI((BackpackContainer) getServerGuiElement(ID, player, world, x, y, z));
			case CAMPFIRE_TILEENTITY:
				return new CampFireGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
			case ENCHANTMENT_BLOCK:
				return new GuiEnchantment(player.inventory, world, x, y, z, null);
			case BREWING_STAND_BLOCK:
				return new BrewingStandGUI(player.inventory, (BrewingContainer)getServerGuiElement(ID, player, world, x, y, z));
			default:
				return null;
		}
	}

}
