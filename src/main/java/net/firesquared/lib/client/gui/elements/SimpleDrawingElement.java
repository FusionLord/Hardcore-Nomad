package net.firesquared.lib.client.gui.elements;

import net.firesquared.lib.client.gui.helper.IQuadDrawer;
import net.firesquared.lib.client.gui.helper.TexturedQuadDrawer;

public class SimpleDrawingElement implements IGuiElement
{
	protected IQuadDrawer drawer;
	protected int x, y;
	protected boolean drawInForeground = true;
	
	public SimpleDrawingElement(IQuadDrawer drawConfig)
	{
		this.drawer = drawConfig;
	}
	
	public SimpleDrawingElement(IQuadDrawer drawConfig, int x, int y, boolean drawForeground)
	{
		this.drawer = drawConfig;
		this.x = x;
		this.y = y;
		drawInForeground = drawForeground;
	}

	@Override
	public int getHeight()
	{
		return drawer.getHeight();
	}

	@Override
	public int getWidth()
	{
		return drawer.getWidth();
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

	@Override
	public void drawBackground()
	{
		if(!drawInForeground)
			drawer.draw(x, y);
	}

	@Override
	public void drawForeground()
	{
		if(drawInForeground)
			drawer.draw(x, y);
	}
	
}
