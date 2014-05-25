package net.firesquared.hardcorenomad.helpers;

import net.firesquaredcore.helper.Vector3n;
import net.minecraft.nbt.NBTTagCompound;

public final class NBTHelper
{
	public static void setXY(NBTTagCompound tag, String baseName, int x, int y)
	{
		tag.setInteger(baseName+X, x);
		tag.setInteger(baseName+Y, y);
	}
	
	public static Vector3n getXY(NBTTagCompound tag, String baseName, Integer x, Integer y, Integer z)
	{
		if(tag == null || !tag.hasKey(baseName + X) || !tag.hasKey(baseName + Y) || !tag.hasKey(baseName + Z))
			return null;
		Vector3n vect = new Vector3n();
		vect.x = tag.getInteger(baseName + X);
		vect.y = tag.getInteger(baseName + Y);
		vect.z = tag.getInteger(baseName + Z);
		return vect;
	}
	public static void setXYZ(NBTTagCompound tag, String baseName, Vector3n loc)
	{
		setXY(tag, baseName, loc.x, loc.y);
		tag.setInteger(baseName+Z, loc.z);
	}
	public static void setXYZ(NBTTagCompound tag, String baseName, int x, int y, int z)
	{
		setXY(tag, baseName, x, y);
		tag.setInteger(baseName+Z, z);
	}
	
	public static Vector3n getXYZ(NBTTagCompound tag, String baseName)
	{
		if(tag == null || !tag.hasKey(baseName + X) || !tag.hasKey(baseName + Y) || !tag.hasKey(baseName + Z))
			return null;
		Vector3n vect = new Vector3n();
		vect.x = tag.getInteger(baseName + X);
		vect.y = tag.getInteger(baseName + Y);
		vect.z = tag.getInteger(baseName + Z);
		return vect;
	}
	public static Vector3n getXY(NBTTagCompound tag, String baseName)
	{
		if(tag == null || !tag.hasKey(baseName + X) || !tag.hasKey(baseName + Y))
			return null;
		Vector3n vect = new Vector3n();
		vect.x = tag.getInteger(baseName + X);
		vect.y = tag.getInteger(baseName + Y);
		return vect;
	}
	
	//public static final String CURRENTLEVEL = "currentLevel";
	public static final String X = "PosX", Y = "PosY", Z = "PosZ";
	public static final String OFFSET = "offset";
	public static final String COMPONENTTYPE = "componentType";
	public static final String COMINV = "componentInventory";
	public static final String STGINV = "storageInventory";
	public static final String SLOT = "slot";
	public static final String UPGRADESLOT = "upgradeSlot";
	public static final String ARMORSLOT = "armorSlot";
	public static final String IS_DEPLOYED = "isDeployed";
	public static final String HAS_PARRENT_BACKPACK = "hasParrentBackpack";
	public static final String PARRENT_BACKPACK_LOCATION = "parrentBackpackLocation";
}
