package net.firesquared.hardcorenomad.helpers.enums;

import net.minecraftforge.common.util.ForgeDirection;

public enum EnumSlotCoordinateOffsets
{	
	WEST(-3, 0, 0),
	NORTHWEST(-3,0,-3),
	NORTH(0, 0, -3),
	NORTHEAST(3,0,-3),
	EAST(3, 0, 0),
	SOUTHEAST(3,0,3),
	SOUTH(0,0,3),
	SOUTHWEST(-3,0,3),
	CENTERWEST(-1,0,0),
	CENTERNORTH(0,0,-1),
	CENTEREAST(1,0,0),
	CENTERSOUTH(0,0,1)
	;
	public int x,y,z;
	private EnumSlotCoordinateOffsets(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
