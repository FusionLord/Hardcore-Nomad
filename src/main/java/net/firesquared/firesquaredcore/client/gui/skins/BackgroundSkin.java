package net.firesquared.firesquaredcore.client.gui.skins;

import net.minecraft.util.ResourceLocation;

public class BackgroundSkin
{
	public static final BackgroundSkin defualt = 
			new BackgroundSkin(new ResourceLocation("firesquaredcore:textures/gui/gui.png"));
	public final ResourceLocation texture;
	
	public BackgroundSkin(ResourceLocation compoundTexture)
	{
		this.texture = compoundTexture;
	}
	public BackgroundSkin(String compoundTexture)
	{
		this.texture = new ResourceLocation(compoundTexture);
	}
	public static BackgroundSkin getDefualt()
	{
		return defualt;
	}
}
