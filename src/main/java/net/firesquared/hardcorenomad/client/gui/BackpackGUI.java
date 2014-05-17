package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class BackpackGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("hardcorenomad:gui/backpack.png");
	boolean isPlaced;
	static final int rowStart = 24;
	public GuiButton[] buttons = new GuiButton[]
	{
		new GuiButton(0, 8, 8, 18, 18, "All"),
		new GuiButton(1, 8 + 1*18, rowStart, 18, 18, "1"),
		new GuiButton(2, 8 + 2*18, rowStart, 18, 18, "2"),
		new GuiButton(3, 8 + 3*18, rowStart, 18, 18, "3"),
		new GuiButton(4, 8 + 4*18, rowStart, 18, 18, "4"),
		new GuiButton(5, 8 + 5*18, rowStart, 18, 18, "5"),
		new GuiButton(6, 8 + 6*18, rowStart, 18, 18, "6"),
		new GuiButton(7, 8 + 7*18, rowStart, 18, 18, "7"),
		new GuiButton(8, 8 + 8*18, rowStart, 18, 18, "8"),
		new GuiButton(9, 8 + 9*18, rowStart, 18, 18, "9")
	};

	public BackpackGUI(BackpackContainer par1Container)
	{
		super(par1Container);
		isPlaced = par1Container.isPlaced;
		for(GuiButton b : buttons)
		{
			b.enabled = true;
			buttonList.add(b);
		}
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
