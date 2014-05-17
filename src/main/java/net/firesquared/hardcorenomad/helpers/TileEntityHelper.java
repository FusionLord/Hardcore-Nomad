package net.firesquared.hardcorenomad.helpers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class TileEntityHelper
{
	public static <T> T getTileEntity(IBlockAccess access, int x, int y, int z, Class<T> clazz)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		return !clazz.isInstance(te) ? null : (T) te;
	}
}
