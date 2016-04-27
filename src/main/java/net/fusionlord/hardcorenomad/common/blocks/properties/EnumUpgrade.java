package net.fusionlord.hardcorenomad.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public static List<EnumUpgrade> getLevelsThrough(int range)
	{
		return Arrays.asList(values()).subList(0, range + 1);
	}
}
