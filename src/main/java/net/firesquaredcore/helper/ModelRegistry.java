package net.firesquaredcore.helper;

import java.util.ArrayList;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.firesquaredcore.helper.Helper;



public class ModelRegistry
{
	private static ArrayList<IModelCustom[]> models = new ArrayList<IModelCustom[]>();
	private static ArrayList<ResourceLocation[]> textures = new ArrayList<ResourceLocation[]>();
	
	private static void add(IModelEnum modelGroup)
	{
		IModelCustom[] mg = new IModelCustom[modelGroup.getModelCount()];
		ResourceLocation[] rg = new ResourceLocation[modelGroup.getTextureCount()];
		String path;
		for (int i = 0; i < mg.length; i++)
		{
			path = modelGroup.getRelativeFilepath() + (mg.length > 1 ? i + 1 : "") + ".obj";
			Helper.getLogger().debug("loading model from "+path);
			mg[i] = AdvancedModelLoader.loadModel(new ResourceLocation(path));
		}
		for (int i = 0; i < rg.length; i++)
		{
			path = modelGroup.getRelativeFilepath() + (rg.length > 1 ? i + 1 : "") + ".png";
			rg[i] = new ResourceLocation(path);
		}
		models.add(mg);
		textures.add(rg);
	}

	public static IModelCustom getModel(IModelEnum model)
	{
		return getModel(model, 0);
	}

	public static IModelCustom getModel(IModelEnum model, int number)
	{
		return models.get(model.getRegID())[number];
	}

	public static ResourceLocation getTexture(IModelEnum model)
	{
		return getTexture(model, 0);
	}

	public static ResourceLocation getTexture(IModelEnum model, int number)
	{
		return textures.get(model.getRegID())[number];
	}

	public static void bindTexture(ResourceLocation texture)
	{
		FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
	}

	public static int registerModel(IModelEnum model)
	{
		int ID = models.size();
		add(model);
		return ID;
	}
}
