package net.fusionlord.hardcorenomad.client.render.tileentity;

import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.function.Function;

public abstract class TESRWRMRL<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IResourceManagerReloadListener
{
	private static boolean firstRun = true;

	public TESRWRMRL()
	{
		MinecraftForge.EVENT_BUS.register(this);
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		if (firstRun)
		{
			firstRun = false;
			return;
		}
		onReload(resourceManager);
	}

	abstract void onReload(IResourceManager resourceManager);

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTextureStitch(TextureStitchEvent event)
	{
		for(ResourceLocation texture : getTextures())
		{
			event.getMap().registerSprite(texture);
			LogHelper.debug(">>>: Registering texture: " + texture.toString());
		}
	}

	abstract List<ResourceLocation> getTextures();

	Function<ResourceLocation, TextureAtlasSprite> textureGetter()
	{
		return resourceLocation -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(resourceLocation.toString());
	}
}
