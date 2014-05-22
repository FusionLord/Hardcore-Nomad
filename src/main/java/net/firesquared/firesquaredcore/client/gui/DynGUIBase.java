package net.firesquared.firesquaredcore.client.gui;

import net.firesquared.firesquaredcore.client.gui.backgrounds.GUIBackgroundProvider;
import net.firesquared.firesquaredcore.client.gui.backgrounds.SimpleRectangleBackground;
import net.firesquared.firesquaredcore.client.gui.elements.IGuiElement;
import net.firesquared.firesquaredcore.client.gui.elements.SlotElement;
import net.firesquared.firesquaredcore.client.gui.skins.BackgroundSkin;
import net.firesquared.firesquaredcore.client.gui.widgets.IWidget;
import net.firesquared.firesquaredcore.helper.Helper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

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
	final BackgroundSkin skin;
	
	List<IGuiElement> elements;
	
	private GUIBackgroundProvider background;

	public DynGUIBase(T container)
	{
		this(container, BackgroundSkin.defualt);
	}

	public DynGUIBase(T container, BackgroundSkin skin)
	{
		super(container);
		this.container = container;
		this.skin = skin;
		Helper.getLogger().debug("I'm logging from the FireSquared Library!");
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
		
		background = getBackgroundProvider();
	}

	protected GUIBackgroundProvider getBackgroundProvider()
	{
		return new SimpleRectangleBackground(skin, guiWidth, guiHeight);
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
		background.drawForeground();
		for(IGuiElement element : elements)
			element.drawForeground();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(guiLeft, guiTop, 0f);
		background.drawBackground();
		for (IGuiElement element : elements)
			element.drawBackground();
		GL11.glTranslatef(-guiLeft, -guiTop, 0f);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		for (IGuiElement element : elements)
			if(element instanceof IWidget)
				((IWidget) element).update();
	}

	protected final DynGUIBase<T> addElement(IGuiElement element)
	{
		elements.add(element);
		return this;
	}
}
