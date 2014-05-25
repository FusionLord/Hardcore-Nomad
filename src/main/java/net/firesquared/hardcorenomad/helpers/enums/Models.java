package net.firesquared.hardcorenomad.helpers.enums;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquaredcore.helper.IModelEnum;
import net.firesquaredcore.helper.ModelRegistry;

public enum Models implements IModelEnum
{
	//The name of the enum variable is used for file path and name...
	//fileName(modelCount, textureCount)
	BACKPACK(1, 4),
	BEDROLL,
	BEDROLL_PILLOW,
	BEDROLL_MATTING,
	BEDROLL_LEANTO,
	BEDROLL_TENT,
	CAMPFIRE,
	CAMPFIRE_OPENOVEN,
	CAMPFIRE_SPIT,
	ENCHANTINGTABLE(5, 1),
	ROCK(4, 1),
	SLINGSHOT,
	;
	
	public final int modelCount;
	public final int textureCount;
	public final String fileName;
	public final int registryID;

	Models()
	{
		this(1, 1);
	}

	Models(final int modelCount, final int textureCount)
	{
		final String[] path = name().toLowerCase().split("_");
		fileName = String.format("%s:models/%s/%s",
				Helper.Strings.MOD_ID, path[0], path.length > 1 ? path[1] : path[0]);
		this.modelCount = modelCount;
		this.textureCount = textureCount;
		registryID = ModelRegistry.registerModel(this);
	}

	@Override
	public int getRegID()
	{
		return registryID;
	}

	@Override
	public int getModelCount()
	{
		return modelCount;
	}

	@Override
	public int getTextureCount()
	{
		return textureCount;
	}

	@Override
	public String getRelativeFilepath()
	{
		return fileName;
	}
}
