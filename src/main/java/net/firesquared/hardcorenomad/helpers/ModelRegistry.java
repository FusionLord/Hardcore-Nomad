package net.firesquared.hardcorenomad.helpers;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;



public class ModelRegistry
{
	public static IModelCustom[][] models;
	public static ResourceLocation[][] textures;

	public static void reinitialise()
	{
		models = null;
		textures = null;
		initialise();
	}

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

	public static void bindTexture(ResourceLocation texture)
	{
		FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
	}
}
