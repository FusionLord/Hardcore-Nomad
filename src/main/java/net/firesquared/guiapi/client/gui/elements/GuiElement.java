package net.firesquared.guiapi.client.gui.elements;

import net.firesquared.guiapi.client.gui.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class GuiElement
{
	int x;
	int y;
	int width;
	int height;
	int uMin;
	int UMax;
	int vMin;
	int vMax;
	ResourceLocation texture;

	public abstract void draw();
	public abstract void update();

	/***
	 * Loads texture from file.
	 * @param textureName The texture file you want to load. (ie: "Minecraft:texture/blocks/oak_planks_top.png")
	 */
	public void loadTexture(String textureName)
	{
		texture = new ResourceLocation(textureName);
	}

	/***
	 * <p>
	 *     Draws texture loaded with {@link #loadTexture(String) loadTexture}
	 * with parameters:
	 *  - {@link #x x} position
	 *  - {@link #y y} position
	 *  - {@link #width width}
	 *  - {@link #height height}
	 *  - {@link #uMin uMin}
	 *  - {@link #vMin vMin}
	 * </p>
	 */
	public void drawTexture()
	{
		if (texture == null)
		{
			Logger.error("Missing texture!");
		}
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)x,          (double)y + height,     0, (double)uMin * f,           (double)(vMin + height) * f1);
		tessellator.addVertexWithUV((double)x + width,  (double)y + height,     0, (double)(uMin + width) * f, (double)(vMin + height) * f1);
		tessellator.addVertexWithUV((double)x + width,  (double)y,              0, (double)(uMin + width) * f, (double)vMin * f1);
		tessellator.addVertexWithUV((double)x,          (double)y,              0, (double)uMin * f,           (double)vMin * f1);
		tessellator.draw();
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public int getY()
	{
		return y;
	}

	public int getX()
	{
		return x;
	}
}
