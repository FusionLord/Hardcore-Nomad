package net.firesquared.guiapi.client.gui;

import net.firesquared.guiapi.client.gui.elements.GuiElement;
import net.firesquared.guiapi.client.gui.elements.SlotElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.ArrayList;

public class DynamicGUI extends GuiContainer
{
	Container container;
	int guiWidth;
	int guiHeight;
	ResourceLocation texture;

	List<GuiElement> elements;

	public DynamicGUI(Container container)
	{
		super(container);
		this.container = container;
		this.texture = new ResourceLocation("guiapi:textures/gui/gui.png");
	}

	public DynamicGUI(Container container, String textureFile)
	{
		super(container);
		this.container = container;
		this.texture = new ResourceLocation(textureFile);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		elements = new ArrayList<GuiElement>();
		int newWidth = 0;;
		int newHeight = 0;
		for (Slot slot : (ArrayList<Slot>)container.inventorySlots)
		{
			elements.add(new SlotElement(slot));
		}
		for (GuiElement element : elements)
		{
			if (element.getX() + element.getWidth() + 7 > newWidth)
				newWidth = element.getX() + element.getWidth() + 7;
			if (element.getY() + element.getHeight() + 7 > newHeight)
				newHeight = element.getY() + element.getHeight() + 7;
		}
		guiWidth = newWidth;
		guiHeight = newHeight;
		this.xSize = guiWidth;
		this.ySize = guiHeight;
	}

	/***
	 *
	 * @param mouseX MousePosition X
	 * @param mouseY MousePosition Y
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(guiLeft, guiTop, 0f);
		drawDynamicBackGround();
		for (GuiElement element : elements)
			element.draw();
		GL11.glTranslatef(-guiLeft, -guiTop, 0f);
	}

	private void drawDynamicBackGround()
	{
		// Draw Filler
		drawTexture(2, 2, guiWidth - 5, guiHeight - 5, 5, 1, 5, 1);
		// Draw TopBar
		drawTexture(2, 0, guiWidth - 5, 3, 5, 1, 0, 3);
		// Draw BottomBar
		drawTexture(2, guiHeight - 3, guiWidth - 5, 3, 5, 1, 8, 3);
		// Draw LeftBar
		drawTexture(0, 2, 3, guiHeight - 5, 0, 3, 5, 1);
		// Draw RightBar
		drawTexture(guiWidth - 3, 2, 3, guiHeight - 4, 8, 3, 5, 1);
		// Draw TopLeft Corner
		drawTexture(0, 0, 3, 3, 0, 3, 0, 3);
		// Draw TopRight Corner
		drawTexture(guiWidth - 4, 0, 4, 4, 7, 4, 0, 4);
		// Draw BottomLeft Corner
		drawTexture(0, guiHeight - 3, 3, 3, 0, 3, 7, 3);
		// Draw BottomRight Corner
		drawTexture(guiWidth - 4, guiHeight - 4, 4, 4, 7, 4, 7, 4);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		for (GuiElement element : elements)
			element.update();
	}

	public void addElement(GuiElement element)
	{
		elements.add(element);
	}


	public void drawTexture(int x, int y, int width, int height, int uMin, int uMax, int vMin, int vMax)
	{
		if (texture == null)
		{
			Logger.error("Missing texture!");
		}
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)x,          (double)y + height,     0, (double)uMin * f,          (double)(vMin + vMax) * f1);
		tessellator.addVertexWithUV((double)x + width,  (double)y + height,     0, (double)(uMin + uMax) * f, (double)(vMin + vMax) * f1);
		tessellator.addVertexWithUV((double)x + width,  (double)y,              0, (double)(uMin + uMax) * f, (double)vMin * f1);
		tessellator.addVertexWithUV((double)x,          (double)y,              0, (double)uMin * f,          (double)vMin * f1);
		tessellator.draw();
	}
}
