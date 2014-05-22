package net.firesquared.lib.client.gui.elements;

import net.firesquared.lib.client.gui.widgets.IWidget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.opengl.GL11;

public class InfoPanelControlerEl implements IWidget
{
	private boolean open, moving;
	private static final int framesToOpen = 15;
	private int x,y,width,height, widthLow, heightLow;
	private float progress = 0;
	public String descriptionText;
	final FontRenderer font;
	final IGuiElement child;
	

	public InfoPanelControlerEl(FontRenderer font, IGuiElement child, int widthLow, int heightLow, 
			int widthHigh, int heightHigh, String description, int x, int y)
	{
		this.width = widthHigh;
		this.height = heightHigh;
		this.widthLow = widthLow;
		this.heightLow = heightLow;
		descriptionText = description;
		this.font = font;
		this.x = x;
		this.y = y;
		this.child = child;
	}

	public void doOpen()
	{
		open = false;
		moving = true;
	}

	public void doClose()
	{
		open = true;
		moving = true;
	}

	public boolean isOpen()
	{
		return open;
	}

	public boolean isMoving()
	{
		return moving;
	}

	public int getMovedY()
	{
		return (int) (progress * (height - heightLow));
	}

	@Override
	public void update()
	{
		if (moving && !open)
		{
			if((progress += 1.0f / framesToOpen) > 1)
			{
				progress = 1;
				open = true;
				moving = false;
			}
		}
		if (moving && open)
		{
			if ((progress -= 1.0f / framesToOpen) < 0)
			{
				progress = 0;
				open = false;
				moving = false;
			}
		}

	}

	@Override
	public void drawBackground()
	{
		GL11.glTranslatef(x+getMovedX(), y+getMovedY(), 0);
		child.drawBackground();
		child.drawForeground();
	}

	@Override
	public void drawForeground()
	{
	}

	public int getMovedX()
	{
		return (int) (progress * (width - widthLow));
	}
	
	public boolean isClickInBounds(int clickX, int clickY)
	{
		return clickX > x && clickY > y && 
				clickX < x + width + getMovedX() && 
				clickY < y + height + getMovedY();
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public int getWidth()
	{
		return width;
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
}
