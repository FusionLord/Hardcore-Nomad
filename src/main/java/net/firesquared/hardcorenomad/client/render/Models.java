package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.Helper;

public enum Models 
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
	final String fileName;

	Models(final int modelCount, final int textureCount)
	{
		final String[] path = this.name().toLowerCase().split("_");
		this.fileName = String.format("%s:models/%s/%s",
				Helper.MOD_ID, path[0], (path.length > 1 ? path[1] : path[0]));
		this.modelCount = modelCount;
		this.textureCount = textureCount;
	}
}
