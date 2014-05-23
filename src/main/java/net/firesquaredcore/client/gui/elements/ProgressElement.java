package net.firesquaredcore.client.gui.elements;

import net.firesquaredcore.client.gui.widgets.IWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class ProgressElement implements IWidget
{
    public ProgressElement(IProgressElementDataProvider dataProvider, ResourceLocation tex, int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dataProvider = dataProvider;
		this.tex = tex;
	}

	float progress;
	protected static final int emptyColor = 0x777777;
    protected int x,y,width,height;
    protected final IProgressElementDataProvider dataProvider;
    protected final ResourceLocation tex;
    protected final TextureManager texMan = Minecraft.getMinecraft().getTextureManager();
    protected final Tessellator tessellator = Tessellator.instance;

    @Override
    public void update()
    {
    	progress = dataProvider.getProgress(this);
    }

    @Override
    public void drawBackground()
    {
//        drawStrechedTexture(x, y, u, v, width, height);
//        drawStrechedTexture(x + 1, y + 1, u, v + 1, width - 2, height - 2);
		texMan.bindTexture(tex);
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(emptyColor);
		tessellator.addVertexWithUV(x, 			y + height,     0, 0, 0);
		tessellator.addVertexWithUV(x + width,	y + height,     0, 1, 0);
		tessellator.addVertexWithUV(x + width,  y,              0, 1, 1);
		tessellator.addVertexWithUV(x,          y,              0, 0, 1);
		tessellator.draw();
		
		texMan.bindTexture(tex);
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0xFFFFFF);
		tessellator.addVertexWithUV(x, 			y + height,     0, 0, 0);
		tessellator.addVertexWithUV(x + width * progress,	y + height,     0, progress, 0);
		tessellator.addVertexWithUV(x + width * progress,  y,              0, progress, 1);
		tessellator.addVertexWithUV(x,          y,              0, 0, 1);
		tessellator.draw();
    }
    
	private void drawPartialRect()
	{
		
	}

	@Override public void drawForeground()
	{}

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
