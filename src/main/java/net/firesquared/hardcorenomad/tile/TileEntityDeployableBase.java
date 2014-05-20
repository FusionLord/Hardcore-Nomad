package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

enum ComponentType
{
	ANVIL,
	BEDROLL,
	BREWINGSTAND,
	CAMPFIRE,
	COBBLEGENERATOR,
	CRAFTINGTABLE,
	ENCHANTINGTABLE,
	BACKPACK,
	;
}

public class TileEntityDeployableBase extends TileEntity
{

	private int currentLevel;
	private ComponentType componentType;
	private int xOffset;
	private int yOffset;
	private int zOffset;

	public TileEntityDeployableBase(ComponentType componentType)
	{
		super();
		this.componentType = componentType;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger(NBTHelper.CURRENTLEVEL, currentLevel);
		tag.setInteger(NBTHelper.COMPONENTTYPE, componentType.ordinal());
		tag.setInteger(NBTHelper.XOFFSET, xOffset);
		tag.setInteger(NBTHelper.YOFFSET, yOffset);
		tag.setInteger(NBTHelper.ZOFFSET, zOffset);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		currentLevel = tag.getInteger(NBTHelper.CURRENTLEVEL);
		componentType = ComponentType.values()[tag.getInteger(NBTHelper.COMPONENTTYPE)];
		xOffset = tag.getInteger(NBTHelper.XOFFSET);
		yOffset = tag.getInteger(NBTHelper.YOFFSET);
		zOffset = tag.getInteger(NBTHelper.ZOFFSET);
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public ComponentType getComponentType()
	{
		return componentType;
	}

	public int getZOffset()
	{
		return zOffset;
	}

	public int getXOffset()
	{
		return xOffset;
	}

	public int getYOffset()
	{
		return yOffset;
	}

}
