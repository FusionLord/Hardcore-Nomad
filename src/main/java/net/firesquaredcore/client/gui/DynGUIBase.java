package net.firesquaredcore.client.gui;

import net.firesquaredcore.client.gui.backgrounds.GUIBackgroundProvider;
import net.firesquaredcore.client.gui.backgrounds.SimpleRectangleBackground;
import net.firesquaredcore.client.gui.elements.IGuiElement;
import net.firesquaredcore.client.gui.elements.SlotElement;
import net.firesquaredcore.client.gui.skins.BackgroundSkin;
import net.firesquaredcore.client.gui.widgets.IWidget;
import net.firesquaredcore.helper.Helper;
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
	
	protected List<IGuiElement> elements;
	
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

		guiLeft = width / 2 - xSize / 2;
		
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
		
		for(IGuiElement element : elements)
			element.drawForeground();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft, guiTop, 0);
		background.drawBackground();
		background.drawForeground();
		for (IGuiElement element : elements)
			element.drawBackground();
		GL11.glPopMatrix();
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
