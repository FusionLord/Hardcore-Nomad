package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPackOLD;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class BackpackGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("hardcorenomad:gui/GUIBackpack1.png");
	boolean isPlaced;
	static final int rowStart = 28, columnStart = 7, size = 18;
	public GuiButton[] buttons;
	BackpackContainer container;

	public BackpackGUI(BackpackContainer par1Container)
	{
		super(par1Container);
		container = par1Container;
		isPlaced = par1Container.isPlaced;
		xSize = 250;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		buttons = new GuiButton[]
		{
			new GuiButton(0, this.guiLeft + 110, this.guiTop + 6, size, size, "U"),
			new GuiButton(1, this.guiLeft + columnStart, this.guiTop + 6, size, size, "Dep"),
			new GuiButton(2, this.guiLeft + columnStart + 0*18, this.guiTop + rowStart, size, size, "1"),
			new GuiButton(3, this.guiLeft + columnStart + 1*18, this.guiTop + rowStart, size, size, "2"),
			new GuiButton(4, this.guiLeft + columnStart + 2*18, this.guiTop + rowStart, size, size, "3"),
			new GuiButton(5, this.guiLeft + columnStart + 3*18, this.guiTop + rowStart, size, size, "4"),
			new GuiButton(6, this.guiLeft + columnStart + 4*18, this.guiTop + rowStart, size, size, "5"),
			new GuiButton(7, this.guiLeft + columnStart + 5*18, this.guiTop + rowStart, size, size, "6"),
			new GuiButton(8, this.guiLeft + columnStart + 6*18, this.guiTop + rowStart, size, size, "7"),
			new GuiButton(9, this.guiLeft + columnStart + 7*18, this.guiTop + rowStart, size, size, "8"),
			new GuiButton(10, this.guiLeft + columnStart + 8*18, this.guiTop + rowStart, size, size, "9"),
		};
		for(GuiButton b : buttons)
		{
			if (b.id == 0)
				b.enabled = true;
			else
				b.enabled = isPlaced;
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
	protected void actionPerformed(GuiButton button)
	{
		TileEntityBackPackOLD te = ((TileEntityBackPackOLD)container.backPack);
		Reference.PACKET_HANDLER.sendToServer(new ButtonPacket(te.xCoord, te.yCoord, te.zCoord, button.id));
		
		super.actionPerformed(button);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
	}

	
}
