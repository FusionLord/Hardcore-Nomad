package net.firesquared.hardcorenomad.helpers.enums;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.firesquaredcore.helper.IModelEnum;
import net.firesquared.firesquaredcore.helper.ModelRegistry;

public enum Models implements IModelEnum
{
	//The name of the enum variable is used for file path and name...
	//fileName(modelCount, textureCount)
	BACKPACK(1, 4),
	BEDROLL(1, 1),
	BEDROLL_PILLOW(1, 1),
	BEDROLL_MATTING(1, 1),
	CAMPFIRE(1, 1),
	ENCHANTINGTABLE(5, 1),
	ROCK(4, 1),
	SLINGSHOT(1, 1),
	;
	
	public final int modelCount;
	public final int textureCount;
	public final String fileName;
	public final int registryID;
	
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
