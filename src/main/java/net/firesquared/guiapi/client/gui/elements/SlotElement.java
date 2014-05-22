package net.firesquared.guiapi.client.gui.elements;

import net.firesquared.guiapi.client.gui.DrawConfig;
import net.firesquared.guiapi.client.gui.GUISkin;
import net.minecraft.inventory.Slot;

public class SlotElement extends SimpleDrawingElement
{
	private static final int slotSize = 18;
	protected final Slot slot;
	public SlotElement(Slot slot, GUISkin skin)
	{
		super(new DrawConfig(skin.texture, 0, slotSize, 11, 11+slotSize));
		this.x = slot.xDisplayPosition - 1;
		this.y = slot.yDisplayPosition - 1;
		drawConfig.setWH(slotSize,slotSize);
		this.slot = slot;
	}
	public SlotElement(Slot slot)
	{
		this(slot, GUISkin.defualt);
	}
}
