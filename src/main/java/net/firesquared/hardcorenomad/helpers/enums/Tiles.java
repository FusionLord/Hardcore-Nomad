package net.firesquared.hardcorenomad.helpers.enums;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquared.hardcorenomad.tile.campcomponents.*;
import net.firesquaredcore.helper.Vector3n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Tiles
{
	ANVIL(TileEntityAnvil.class, "anvil"),
	BACKPACK(TileEntityBackPack.class, "backpack"),
	BEDROLL(TileEntityBedRoll.class, "bedroll"),
	BREWING_STAND(TileEntityDeployableBase.class, "brewingstand"),
	CAMPFIRE(TileEntityCampFire.class, "campfire"),
	COBBLEGEN(TileEntityCobbleGenerator.class, "cobblegen"),
	CRAFTING(TileEntityCrafting.class, "crafting"),
	ENCHANT_TABLE_COMPACT(TileEntityEnchantmentTable.class, "enchantmenttable"),
	STORAGE(TileEntityDeployableBase.class, "storage"),
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
	
	public static <T extends TileEntity> T getTileEntity(IBlockAccess access, Vector3n loc)
	{
		return Tiles.<T>getTileEntity(access, loc.x, loc.y, loc.z);
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
