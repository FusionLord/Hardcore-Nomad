package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.CampFireContainer;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CampFireGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("textures/gui/container/furnace.png");
	private TileEntityCampFire tileEntityCampFire;

	public CampFireGUI(Container serverGuiElement)
	{
		super(serverGuiElement);
		this.tileEntityCampFire = ((CampFireContainer)serverGuiElement).tileEntityCampFire;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		String s = this.tileEntityCampFire.hasCustomInventoryName() ? this.tileEntityCampFire.getInventoryName() : I18n
				.format(this.tileEntityCampFire.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.tileEntityCampFire.isBurning())
		{
			i1 = this.tileEntityCampFire.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = this.tileEntityCampFire.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
