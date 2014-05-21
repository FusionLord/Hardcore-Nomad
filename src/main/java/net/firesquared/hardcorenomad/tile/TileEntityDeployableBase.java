package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;;

public class TileEntityDeployableBase extends TileEntity
{
	private int currentLevel;
	private UpgradeType componentType;
	private int xOffset;
	private int yOffset;
	private int zOffset;

	public TileEntityDeployableBase(UpgradeType componentType)
	{
		super();
		this.componentType = componentType;
	}

	public TileEntityDeployableBase(UpgradeType componentType, int metadata)
	{
		super();
		this.componentType = componentType;
		currentLevel = metadata;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger(NBTHelper.CURRENTLEVEL, currentLevel);
		if(componentType != null)
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
		if(tag.hasKey(NBTHelper.COMPONENTTYPE))
			componentType = UpgradeType.values()[tag.getInteger(NBTHelper.COMPONENTTYPE)];
		else
			componentType = null;
		xOffset = tag.getInteger(NBTHelper.XOFFSET);
		yOffset = tag.getInteger(NBTHelper.YOFFSET);
		zOffset = tag.getInteger(NBTHelper.ZOFFSET);
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public UpgradeType getComponentType()
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
