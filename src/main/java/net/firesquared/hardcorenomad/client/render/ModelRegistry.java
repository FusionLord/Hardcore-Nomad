package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

enum Models {
	//			(fileName, modelCount, textureCount)
	MODEL_BACKPACK("backpack", 1, 4),
	MODEL_BEDROLL("bedroll", 1, 1),
	MODEL_CAMPFIRE("campfire", 1, 1),
	MODEL_ENCHANTING_TABLE("enchanting_table", 5, 1),
	MODEL_ROCKS("rock", 4, 1),
	;

	int modelCount;
	int textureCount;
	String fileName;

	Models(String fileName, int modelCount, int textureCount)
	{
		this.fileName = fileName;
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
				models[model.ordinal()][i] = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID + ":models/" + model.fileName + "/" + model.fileName + (model.modelCount > 1 ? i + 1 : "") + ".obj"));
			}
			for (int i = 0; i < model.textureCount; i++)
			{
				textures[model.ordinal()][i] = new ResourceLocation(Reference.MOD_ID + ":models/" + model.fileName + "/" + model.fileName + (model.textureCount > 1 ? i + 1 : "") + ".png");
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
