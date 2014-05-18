package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.network.PacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class BackpackGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("hardcorenomad:gui/GUIBackpack1.png");
	boolean isPlaced;
	static final int rowStart = 65, columnStart = 114, size = 18;
	public GuiButton[] buttons = new GuiButton[]
	{
		new GuiButton(0, columnStart + 18, 44, size, size, "All"),
		new GuiButton(1, columnStart + 1*18, rowStart, size, size, "1"),
		new GuiButton(2, columnStart + 2*18, rowStart, size, size, "2"),
		new GuiButton(3, columnStart + 3*18, rowStart, size, size, "3"),
		new GuiButton(4, columnStart + 4*18, rowStart, size, size, "4"),
		new GuiButton(5, columnStart + 5*18, rowStart, size, size, "5"),
		new GuiButton(6, columnStart + 6*18, rowStart, size, size, "6"),
		new GuiButton(7, columnStart + 7*18, rowStart, size, size, "7"),
		new GuiButton(8, columnStart + 8*18, rowStart, size, size, "8"),
		new GuiButton(9, columnStart + 9*18, rowStart, size, size, "9")
	};

	public BackpackGUI(BackpackContainer par1Container)
	{
		super(par1Container);
		isPlaced = par1Container.isPlaced;
	}
	
	@Override
	public void initGui()
	{
		for(GuiButton b : buttons)
		{
			b.enabled = true;
			buttonList.add(b);
		}
		super.initGui();
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
	protected void actionPerformed(GuiButton butt)
	{
		switch(butt.id)
		{
			case 0:
				//HardcoreNomad.instance.p
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				
				break;
			case 8:
				
				break;
			case 9:
				
				break;
			
			default:
				break;
		}
		
		super.actionPerformed(butt);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
	}

	
}
