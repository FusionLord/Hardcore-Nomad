


package net.firesquared.lib.client.gui.elements;

public interface IGuiElement
{
	public void drawBackground();
	
	public abstract void  drawForeground();
	
	public int getHeight();
	
	public int getWidth();
	
	public int getY();
	
	public int getX();
	
}