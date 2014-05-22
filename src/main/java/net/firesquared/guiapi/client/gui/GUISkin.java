package net.firesquared.guiapi.client.gui;

import net.minecraft.util.ResourceLocation;

public class GUISkin
{
	public static final GUISkin defualt = 
			new GUISkin(new ResourceLocation("guiapi:textures/gui/gui.png"));
	public final ResourceLocation texture;
	
	public GUISkin(ResourceLocation compoundTexture)
	{
		this.texture = compoundTexture;
	}
	public GUISkin(String compoundTexture)
	{
		this.texture = new ResourceLocation(compoundTexture);
	}
	public static GUISkin getDefualt()
	{
		return defualt;
	}
}
