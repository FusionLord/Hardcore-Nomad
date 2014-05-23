package net.firesquared.hardcorenomad.block.campcomponents;

import java.util.List;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AABBPool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBedRoll extends BlockCampComponent
{

	public BlockBedRoll()
	{
		super(Material.cloth);
		this.setBlockBounds(-0.5f, 0f, 0f, 1.5f, .25f, 1f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityBedRoll();
	}

	@Override
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB p_149743_5_, List p_149743_6_,
			Entity p_149743_7_)
	{       
		AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(getBlockBoundsMinX() + x, getBlockBoundsMinY() + y, getBlockBoundsMinZ() + z,
				getBlockBoundsMaxX() + x, getBlockBoundsMaxY() + y, getBlockBoundsMaxZ() + z);

	    if (axisalignedbb1 != null && p_149743_5_.intersectsWith(axisalignedbb1))
	    {
	        p_149743_6_.add(axisalignedbb1);
	    }
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
}
