package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

enum Models {
	//The name of the enum variable is used for file path and name...
	//fileName(modelCount, textureCount)
	BACKPACK(1, 4),
	BEDROLL(1, 1),
	BEDROLL_PILLOW(1, 1),
	BEDROLL_MATTING(1, 1),
	CAMPFIRE(1, 1),
	ENCHANTINGTABLE(5, 1),
	ROCK(4, 1),
	;

	int modelCount;
	int textureCount;
	String fileName;

	Models(int modelCount, int textureCount)
	{
		String[] path = this.name().toLowerCase().split("_");
		this.fileName = String.format("%s:models/%s/%s",
				Reference.MOD_ID, path[0], (path.length > 1 ? path[1] : path[0]));
		this.modelCount = modelCount;
		this.textureCount = textureCount;
	}
}

public class ModelRegistry
{
	public static IModelCustom[][] models;
	public static ResourceLocation[][] textures;

	public static void initialise()
	{
		models = new IModelCustom[Models.values().length][];
		textures = new ResourceLocation[Models.values().length][];
		for (Models model : Models.values())
		{
			models[model.ordinal()] = new IModelCustom[model.modelCount];
			textures[model.ordinal()] = new ResourceLocation[model.textureCount];
			for (int i = 0; i < model.modelCount; i++)
			{
				LogHelper.debug(model.fileName + (model.modelCount > 1 ? i + 1 : "") + ".obj");
				models[model.ordinal()][i] = AdvancedModelLoader.loadModel(new ResourceLocation(model.fileName + (model.modelCount > 1 ? i + 1 : "") + ".obj"));
			}
			for (int i = 0; i < model.textureCount; i++)
			{
				textures[model.ordinal()][i] = new ResourceLocation(model.fileName + (model.textureCount > 1 ? i + 1 : "") + ".png");
			}
		}
	}

	public static IModelCustom getModel(Models model)
	{
		return getModel(model, 0);
	}

	public static IModelCustom getModel(Models model, int number)
	{
		return models[model.ordinal()][number];
	}

	public static ResourceLocation getTexture(Models model)
	{
		return getTexture(model, 0);
	}

	public static ResourceLocation getTexture(Models model, int number)
	{
		return textures[model.ordinal()][number];
	}

}