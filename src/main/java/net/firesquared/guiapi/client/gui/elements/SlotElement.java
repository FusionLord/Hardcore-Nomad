package net.firesquared.guiapi.client.gui.elements;

import net.minecraft.inventory.Slot;

public class SlotElement extends GuiElement
{
	public SlotElement(Slot slot)
	{
		super();
		this.x = slot.xDisplayPosition - 1;
		this.y = slot.yDisplayPosition - 1;
		this.width = 18;
		this.height = 18;
		this.uMin = 0;
		this.vMin = 11;
		loadTexture("guiapi:textures/gui/gui.png");
	}

	@Override
	public void draw()
	{
		drawTexture();
	}

	@Override
	public void update()
	{}
}
