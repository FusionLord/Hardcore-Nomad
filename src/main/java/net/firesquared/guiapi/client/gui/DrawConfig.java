package net.firesquared.guiapi.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class DrawConfig
{
	public enum BackgroundConfig
	{
		FILLER(5, 6, 5, 6),
		SIDE_TOP(5, 6, 0, 3),
		SIDE_BOTTOM(5, 6, 8, 11),
		SIDE_LEFT(0, 3, 5, 6),
		SIDE_RIGHT(8, 11, 5, 6),
		CORNER_TOP_LEFT(0, 3, 0, 3),
		CORNER_TOP_RIGHT(7, 11, 0, 4),
		CORNER_BOTTOM_LEFT(0, 3, 7, 10),
		CORNER_BOTTOM_RIGHT(7, 11, 7, 11);
		private BackgroundConfig(int uLow, int uHigh, int vLow, int vHigh)
		{
			this.uLow = uLow;
			this.uHigh = uHigh;
			this.vLow = vLow;
			this.vHigh = vHigh;
		}

		public final int uLow, uHigh, vLow, vHigh;
	}
	
	float zLevel = 0;
	public float getZLayer()
	{
		return zLevel;
	}
	public void setZLayer(float z)
	{
		zLevel = z;
	}
	ResourceLocation texture;
	final Tessellator tessellator = Tessellator.instance;
	final TextureManager texMan = Minecraft.getMinecraft().getTextureManager();
	static final float scale = 0.00390625F;
	public DrawConfig(ResourceLocation texture, float uMin, float uMax, float vMin, float vMax, int width, int height)
	{
		this(texture, uMin, uMax, vMin, vMax);
		this.width = width;
		this.height = height;
	}
	public DrawConfig(ResourceLocation texture, float uMin, float uMax, float vMin, float vMax)
	{
		u3 = u0 = uMin * scale;
		v1 = v0 = vMax * scale;
		u1 = u2 = uMax * scale;
		v2 = v3 = vMin * scale;
		this.texture = texture;
	}
	public DrawConfig(ResourceLocation texture, Vector2f uv0, Vector2f uv1, Vector2f uv2, Vector2f uv3)
	{
		u0 = uv0.x;
		v0 = uv0.y;
		u1 = uv1.x;
		v1 = uv1.y;
		u2 = uv2.x;
		v2 = uv2.y;
		u3 = uv3.x;
		v3 = uv3.y;
		this.texture = texture;
	}
	public DrawConfig(ResourceLocation texture, BackgroundConfig configDef)
	{
		this(texture, configDef.uLow, configDef.uHigh, configDef.vLow, configDef.vHigh);
	}
	public final float u0,v0,u1,v1,u2,v2,u3,v3;
	public int width, height;
	public void setWH(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	public void draw(int x,int y)
	{
		draw(x, y, width, height);
	}
	public void draw(int x, int y, int width, int height)
	{
		if (texture == null) Logger.error("Missing texture!");
		texMan.bindTexture(texture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, 			y + height,     zLevel, u0, v0);
		tessellator.addVertexWithUV(x + width,	y + height,     zLevel, u1, v1);
		tessellator.addVertexWithUV(x + width,  y,              zLevel, u2, v2);
		tessellator.addVertexWithUV(x,          y,              zLevel, u3, v3);
		tessellator.draw();
	}
	public void draw(int x, int y, int width, int height, float rotation)
	{
		if (texture == null) Logger.error("Missing texture!");
		texMan.bindTexture(texture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, 			y + height,     zLevel, u0, v0);
		tessellator.addVertexWithUV(x + width,	y + height,     zLevel, u1, v1);
		tessellator.addVertexWithUV(x + width,  y,              zLevel, u2, v2);
		tessellator.addVertexWithUV(x,          y,              zLevel, u3, v3);
		GL11.glRotatef(rotation, 0, 0, 1);
		tessellator.draw();
		GL11.glRotatef(-rotation, 0, 0, 1);
	}
}
