package net.fusionlord.hardcorenomad.common.tileentity;

import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by FusionLord on 4/26/2016.
 */
public class TileEntityUpgradableGeneric extends TileEntityUpgradable
{
	public TileEntityUpgradableGeneric()
	{
		super(EnumFacing.NORTH, EnumUpgrade.WOOD);
	}

	@Override
	void readExtraData(NBTTagCompound compound) {}

	@Override
	void writeExtraData(NBTTagCompound compound) {}
}
