package net.firesquared.lib.client.gui.helper;

import net.firesquared.lib.helper.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ColoredQuadDrawer implements IQuadDrawer
{
	
	float zLevel = 0;
	final Tessellator tessellator = Tessellator.instance;
	public int color0,color1,color2,color3;
	public int width, height;
	public ColoredQuadDrawer(int color)
	{
		this(color, 0, 0);
	}
	public ColoredQuadDrawer(int color, int width, int height)
	{
		this(color,color,color,color,width,height);
	}
	public ColoredQuadDrawer(int color0, int color1, int color2, int color3, int width, int height)
	{
		this.width =width;
		this.height =height;
		this.color0 = color0;
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}

	@Override
	public void draw(int x,int y)
	{
		draw(x, y, width, height);
	}

	@Override
	public void draw(int x, int y, int width, int height)
	{
		tessellator.startDrawingQuads();
		
		tessellator.setColorOpaque_I(color0);
		tessellator.addVertex(x, 			y + height,     zLevel);
		
		tessellator.setColorOpaque_I(color1);
		tessellator.addVertex(x + width,	y + height,     zLevel);
		
		tessellator.setColorOpaque_I(color2);
		tessellator.addVertex(x + width,  y,              zLevel);
		
		tessellator.setColorOpaque_I(color3);
		tessellator.addVertex(x,          y,              zLevel);
		
		tessellator.draw();
	}

	@Override
	public void draw(int x, int y, int width, int height, float rotation)
	{
		tessellator.startDrawingQuads();
		
		tessellator.setColorOpaque_I(color0);
		tessellator.addVertex(x, 			y + height,     zLevel);
		
		tessellator.setColorOpaque_I(color1);
		tessellator.addVertex(x + width,	y + height,     zLevel);
		
		tessellator.setColorOpaque_I(color2);
		tessellator.addVertex(x + width,  y,              zLevel);
		
		tessellator.setColorOpaque_I(color3);
		tessellator.addVertex(x,          y,              zLevel);
		GL11.glRotatef(rotation, 0, 0, 1);
		tessellator.draw();
		GL11.glRotatef(-rotation, 0, 0, 1);
	}

	@Override
	public float getZLayer()
	{
		return zLevel;
	}

	@Override
	public ColoredQuadDrawer setWH(int width, int height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public IQuadDrawer setZLayer(float z)
	{
		zLevel = z;
		return this;
	}
	@Override
	public int getWidth()
	{
		return width;
	}
	@Override
	public int getHeight()
	{
		return height;
	}
}
