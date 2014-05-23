package net.firesquaredcore.client.gui.skins;

import net.firesquaredcore.helper.Helper;
import net.minecraft.util.ResourceLocation;

public class BackgroundSkin
{
	public static final BackgroundSkin defualt = 
			new BackgroundSkin(new ResourceLocation(Helper.Strings.MOD_ID+":textures/gui/gui.png"));
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
