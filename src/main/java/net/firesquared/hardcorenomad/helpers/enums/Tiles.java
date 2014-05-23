package net.firesquared.hardcorenomad.helpers.enums;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Tiles
{
	BACKPACK(TileEntityBackPack.class, "backpack"),
	CAMPFIRE(TileEntityCampFire.class, "campfire"),
	ENCHANT_TABLE_COMPACT(TileEntityEnchantmentTable.class, "enchantmenttable"),
	CRAFTING(TileEntityCrafting.class, "crafting"),
	BEDROLL(TileEntityBedRoll.class, "bedroll")
	;
	public final Class<? extends TileEntity> tileClass;
	private final String ID;
	Tiles(Class<? extends TileEntity> clazz, String identifier)
	{
		tileClass = clazz;
		ID = identifier;
	}
	
	private void register()
	{
		GameRegistry.registerTileEntity(tileClass, "tile." + Helper.Strings.MOD_ID + "." + ID.toLowerCase());
	}
	
	public static void registerAll()
	{
		for(Tiles t : Tiles.values())
			t.register();
	}
	
	public static <T extends TileEntity> T getTileEntity(IBlockAccess access, int x, int y, int z)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		try
		{
			return (T)te;
		}
		catch(ClassCastException e)
		{
			return null;
		}
	}
}
