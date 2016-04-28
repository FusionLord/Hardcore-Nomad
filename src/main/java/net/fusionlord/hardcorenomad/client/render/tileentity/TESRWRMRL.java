package net.fusionlord.hardcorenomad.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TESRWRMRL<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IResourceManagerReloadListener
{
	ResourceLocation texture;

	public TESRWRMRL()
	{
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent event)
	{
		event.getMap().registerSprite(texture);
	}
}
