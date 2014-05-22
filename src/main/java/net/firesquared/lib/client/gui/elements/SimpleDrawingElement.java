package net.firesquared.lib.client.gui.elements;

import net.firesquared.lib.client.gui.helper.DrawConfig;

public class SimpleDrawingElement implements IGuiElement
{
	protected DrawConfig drawConfig;
	protected int x, y;
	
	public SimpleDrawingElement(DrawConfig drawConfig)
	{
		this.drawConfig = drawConfig;
	}
	
	public SimpleDrawingElement(DrawConfig drawConfig, int x, int y)
	{
		this.drawConfig = drawConfig;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw()
	{
		drawConfig.draw(x, y);
	}

	@Override
	public int getHeight()
	{
		return drawConfig.height;
	}

	@Override
	public int getWidth()
	{
		return drawConfig.width;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public int getX()
	{
		return x;
	}
	
	public SimpleDrawingElement setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
}
