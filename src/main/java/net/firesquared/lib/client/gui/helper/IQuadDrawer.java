


package net.firesquared.lib.client.gui.helper;

public interface IQuadDrawer
{
	
	public void draw(int x, int y);
	
	public void draw(int x, int y, int width, int height);
	
	public void draw(int x, int y, int width, int height, float rotation);
	
	public float getZLayer();
	
	public int getWidth();
	
	public int getHeight();
	
	public abstract IQuadDrawer setWH(int width, int height);
	
	public abstract IQuadDrawer setZLayer(float z);
	
}