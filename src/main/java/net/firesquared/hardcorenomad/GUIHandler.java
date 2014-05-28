package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.network.IGuiHandler;
import net.firesquared.hardcorenomad.client.gui.*;
import net.firesquared.hardcorenomad.container.*;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{
	public enum GUIType
	{
		BACKPACK_TILEENTITY(0),
		BACKPACK_ITEMFORM(10),
		CAMPFIRE_TILEENTITY(20),
		CRAFTINGTABLE_BLOCK(30),
		ENCHANTMENT_BLOCK(40),
		ANVIL_BLOCK(50);
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
			case CRAFTINGTABLE_BLOCK:
				return new CraftingTableContainer(player.inventory, world, x, y, z);
			case ENCHANTMENT_BLOCK:
				return new EnchantmentContainer(player.inventory, world, x, y, z);
			case ANVIL_BLOCK:
				return new AnvilContainer(player.inventory, world, x, y, z, player);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Helper.getLogger().debug("Opening GUI #" + ID);
		switch(GUIType.fromID(ID))
		{
			case BACKPACK_TILEENTITY:
			case BACKPACK_ITEMFORM:
				return new BackpackGUI((BackpackContainer) getServerGuiElement(ID, player, world, x, y, z));
			case CAMPFIRE_TILEENTITY:
				return new CampFireGUI((Container) getServerGuiElement(ID, player, world, x, y, z));
			case CRAFTINGTABLE_BLOCK:
				return new CraftingTableGUI(player, world, x, y, z);
			case ENCHANTMENT_BLOCK:
				return new EnchantmentGUI(player.inventory, world, x, y, z, null);
			case ANVIL_BLOCK:
				return new AnvilGUI(player.inventory, world, x, y, z);
			default:
				return null;
		}
	}

}
