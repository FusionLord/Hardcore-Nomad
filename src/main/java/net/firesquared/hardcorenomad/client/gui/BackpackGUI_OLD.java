package net.firesquared.hardcorenomad.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
@Deprecated
public class BackpackGUI_OLD extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("hardcorenomad:gui/GUIBackpack1.png");
	boolean isPlaced;
	static final int rowStart = 28, columnStart = 7, size = 18;
	BackpackContainer container;

	public BackpackGUI_OLD(BackpackContainer par1Container)
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
		if(!isPlaced)
			return;
		List<GuiButton> buttons = new ArrayList<GuiButton>();
		int count;
		buttons.add(new GuiButton(-1, this.guiLeft + 110, this.guiTop + 6, size, size, "U"));
		buttons.add(new GuiButton(100, this.guiLeft + columnStart, this.guiTop + 6, size, size, "Dep"));
		for(count = 0; count < ItemUpgrade.getCampComponentCount(); count++)
			new GuiButton(count, this.guiLeft + columnStart + count*18, this.guiTop + rowStart, size, size, ""+(count+1));
		for(GuiButton b : buttons)
		{
			//perform any necessary enabling or disabling of buttons.
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
		
		if(!isPlaced)
		{
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), 
					container.getThisBackpack(), container.getMySlot() * 18 + 8 + this.guiLeft, 
					18 * 7 + 7 + this.guiTop);
		}	
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(isPlaced)
		{
			TileEntityBackPack te = ((TileEntityBackPack)container.backPack);
			Helper.PACKET_HANDLER.sendToServer(new ButtonPacket(te.xCoord, te.yCoord, te.zCoord, button.id));
		}
		
		super.actionPerformed(button);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
	}

	
}
