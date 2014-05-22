package net.firesquared.lib.client.gui.elements;

import net.firesquared.lib.client.gui.helper.TexturedQuadDrawer;
import net.firesquared.lib.client.gui.skins.BackgroundSkin;
import net.minecraft.inventory.Slot;

public class SlotElement extends SimpleDrawingElement
{
	private static final int slotSize = 18;
	protected final Slot slot;
	public SlotElement(Slot slot, BackgroundSkin skin)
	{
		super(new TexturedQuadDrawer(skin.texture, 0, slotSize, 11, 11+slotSize));
		this.x = slot.xDisplayPosition - 1;
		this.y = slot.yDisplayPosition - 1;
		drawer.setWH(slotSize,slotSize);
		this.slot = slot;
	}
	public SlotElement(Slot slot)
	{
		this(slot, BackgroundSkin.defualt);
	}
}
