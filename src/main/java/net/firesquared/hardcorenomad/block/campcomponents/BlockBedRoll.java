package net.firesquared.hardcorenomad.block.campcomponents;

import java.util.List;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockBedRoll extends BlockCampComponent
{

	public BlockBedRoll()
	{
		super(Material.cloth);
		this.setBlockBounds(-0.5f, 0f, 0f, 1.5f, .25f, 1f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityBedRoll();
	}

	@Override
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB boundingbox, List list,	Entity entity)
	{       
		AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(getBlockBoundsMinX() + x, getBlockBoundsMinY() + y, getBlockBoundsMinZ() + z,
				getBlockBoundsMaxX() + x, getBlockBoundsMaxY() + y, getBlockBoundsMaxZ() + z);

	    if (axisalignedbb1 != null && boundingbox.intersectsWith(axisalignedbb1))
	    {
	        list.add(axisalignedbb1);
	    }
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
	
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.BEDROLL;
	}
}
