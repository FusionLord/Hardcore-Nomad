package net.firesquared.hardcorenomad.helpers;

public enum BackPackType
{
	BACKPACK_BASIC(2,3, false), 
	BACKPACK_IMPROVED(3,4, true), 
	BACKPACK_ADVANCED(3,7, true), 
	BACKPACK_ARMORED(4,8, true);
	
	int storageW;
	int storageH;
	int storageTotal;
	boolean armored;
	BackPackType(int width, int height, boolean allowsArmor)
	{
		storageH = height;
		storageW = width;
		storageTotal = height * width;
		armored = allowsArmor;
	}
	
	public int getStorageCount()
	{
		return storageTotal;
	}
	
	public int getStorageWidth()
	{
		return storageW;
	}
	
	public int getStorageHeight()
	{
		return storageH;
	}
	
	public boolean hasArmorSlot()
	{
		return armored;
	}
}
