package net.firesquared.guiapi.client.gui;

import net.firesquared.guiapi.client.gui.DrawConfig.BackgroundConfig;
import net.firesquared.guiapi.client.gui.elements.IGuiElement;
import net.firesquared.guiapi.client.gui.elements.SlotElement;
import net.firesquared.guiapi.client.gui.widgets.IWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.ArrayList;

/**
 * Sample implementation of a dynamic gui around a container with no special dynamic GUI support
 * @author FireStorm, FusionLord
 *
 * @param <T> Either Container, or a type that extends it; use something specific if
 * you want tighter integration between your GUI elements and the container
 */
public abstract class DynGUIBase<T extends Container> extends GuiContainer
{
	protected final T container;
	protected int guiWidth, guiHeight;
	final GUISkin skin;
	
	List<IGuiElement> elements;
	
	private final List<DrawConfig> bgConfigs = new ArrayList<DrawConfig>();

	public DynGUIBase(T container)
	{
		this(container, GUISkin.defualt);
	}

	public DynGUIBase(T container, GUISkin skin)
	{
		super(container);
		this.container = container;
		this.skin = skin;
		
		BackgroundConfig[] vals = BackgroundConfig.values();
		for(BackgroundConfig bc : vals)
			bgConfigs.add(new DrawConfig(skin.texture, bc));

	}

	@Override
	public void initGui()
	{
		super.initGui();
		elements = new ArrayList<IGuiElement>();
		int newWidth = 0;;
		int newHeight = 0;
		for (Slot slot : (ArrayList<Slot>)container.inventorySlots)
		{
			elements.add(new SlotElement(slot, skin));
		}
		
		//dynamically resize the background to fit all elements
		for (IGuiElement element : elements)
		{
			if (element.getX() + element.getWidth() + 7 > newWidth)
				newWidth = element.getX() + element.getWidth() + 7;
			if (element.getY() + element.getHeight() + 7 > newHeight)
				newHeight = element.getY() + element.getHeight() + 7;
		}
		this.xSize = (guiWidth = newWidth);
		this.ySize = (guiHeight = newHeight);
		
		initBackgroundElements();
	}

	protected void initBackgroundElements()
	{
		bgConfigs.get(0).setWH(guiWidth - 5, guiHeight - 5);//Filler
		
		bgConfigs.get(1).setWH(guiWidth - 5, 3);//Top
		bgConfigs.get(2).setWH(guiWidth - 5, 3);//Bottom
		bgConfigs.get(3).setWH(3, guiHeight - 5);//Left
		bgConfigs.get(4).setWH(3, guiHeight - 4);//Right
		
		bgConfigs.get(5).setWH(3, 3);//Top Left
		bgConfigs.get(6).setWH(4, 4);//Top Right
		bgConfigs.get(7).setWH(3, 3);//Bottom Left
		bgConfigs.get(8).setWH(4, 4);//Bottom Right
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
		for (IGuiElement element : elements)
			element.draw();
		GL11.glTranslatef(-guiLeft, -guiTop, 0f);
	}

	private void drawDynamicBackGround()
	{
		bgConfigs.get(0).draw(2, 2);
		
		bgConfigs.get(1).draw(2, 0);
		bgConfigs.get(2).draw(2, guiHeight - 3);
		bgConfigs.get(3).draw(0, 2);
		bgConfigs.get(4).draw(guiWidth - 3, 2);
		
		bgConfigs.get(5).draw(0, 0);
		bgConfigs.get(6).draw(guiWidth - 4, 0);
		bgConfigs.get(7).draw(0, guiHeight - 3);
		bgConfigs.get(8).draw(guiWidth - 4, guiHeight - 4);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		for (IGuiElement element : elements)
			if(element instanceof IWidget)
				((IWidget) element).update();
	}

	public void addElement(IGuiElement element)
	{
		elements.add(element);
	}
}
