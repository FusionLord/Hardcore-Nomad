package net.firesquared.hardcorenomad.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class BackpackGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("hardcorenomad:gui/backpack.png");
	
	public BackpackGUI(Container par1Container)
	{
		super(par1Container);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int centX = (width - xSize) / 2;
		int centY = (height - ySize) / 2;
		
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(centX, centY, 0, 0, 256, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
	}
	
}
