package net.fusionlord.hardcorenomad.common.tileentity.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TileEntityUtils
{
	public static <T extends TileEntity> T getTileEntity(IBlockAccess worldIn, BlockPos pos, Class<T> tile)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity != null && tile.isInstance(tileEntity))
		{
			return tile.cast(tileEntity);
		}
		return null;
	}
}
