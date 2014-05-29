package net.firesquared.hardcorenomad.client.gui;

import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.container.BrewingContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class BrewingStandGUI extends GuiContainer
{
    private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private final BrewingContainer container;
    public BrewingStandGUI(@SuppressWarnings("unused") InventoryPlayer inventory, BrewingContainer brewingContainer)
    {
        super(brewingContainer);
        container = brewingContainer;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = container.getInventoryName();
        if(container.hasCustomInventoryName())
        	s = I18n.format(container.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x404040);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
	protected void drawGuiContainerBackgroundLayer(float someFloat, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
        int offX = (this.width - this.xSize) / 2;
        int offY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offX, offY, 0, 0, this.xSize, this.ySize);
        int brewTime = container.getBrewTime();

        if (brewTime > 0)
        {
            int visualSize = (int)(28.0F * (1.0F - brewTime / 400.0F));
            
            switch (brewTime / 2 % 7)
            {
                case 0:
                    visualSize = 29;
                    break;
                case 1:
                    visualSize = 24;
                    break;
                case 2:
                    visualSize = 20;
                    break;
                case 3:
                    visualSize = 16;
                    break;
                case 4:
                    visualSize = 11;
                    break;
                case 5:
                    visualSize = 6;
                    break;
                case 6:
                    visualSize = 0;
            }

            if (visualSize > 0)
            {
            	this.drawTexturedModalRect(offX + 97, offY + 16, 176, 0, 9, visualSize);
                this.drawTexturedModalRect(offX + 65, offY + 14 + 29 - visualSize, 185, 29 - visualSize, 12, visualSize);
            }
        }
    }

}
