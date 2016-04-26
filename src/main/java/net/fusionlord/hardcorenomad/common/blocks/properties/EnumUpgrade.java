package net.fusionlord.hardcorenomad.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

import java.util.Arrays;

public enum EnumUpgrade implements IStringSerializable
{
	WOOD,
	STONE,
	IRON,
	GOLD,
	DIAMOND,
	;

	@Override
	public String getName()
	{
		return name().toLowerCase();
	}

	public static EnumUpgrade[] getLevelsThrough(int range)
	{
		return (EnumUpgrade[]) Arrays.asList(values()).subList(0, range).toArray();
	}
}
