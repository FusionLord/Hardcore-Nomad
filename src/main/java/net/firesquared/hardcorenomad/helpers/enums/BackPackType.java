package net.firesquared.hardcorenomad.helpers.enums;

import java.util.Locale;

public enum BackPackType
{
	BACKPACK_BASIC(2,3, false), 
	BACKPACK_IMPROVED(3,4, true), 
	BACKPACK_ADVANCED(3,7, true), 
	BACKPACK_ARMORED(4,8, true);
	
	public final int storageW;
	public final int storageH;
	public final int storageTotal;
	public final boolean hasArmorSlot;
	public final String unlocalizedPostfix;
	
	BackPackType(int width, int height, boolean allowsArmor)
	{
		storageH = height;
		storageW = width;
		storageTotal = height * width;
		hasArmorSlot = allowsArmor;
		this.unlocalizedPostfix = name().toLowerCase(Locale.ENGLISH).replaceAll("_", "");
	}
	
	public static BackPackType fromLevel(int level)
	{
		if(level < 0)
			level = 0;
		else if(level >= values().length)
			level = values().length - 1;
		return values()[level];
	}

	public BackPackType next()
	{
		try
		{
			return BackPackType.values()[ordinal()+1];
		}
		catch(Exception e)
		{
			return this;
		}
	}
}
