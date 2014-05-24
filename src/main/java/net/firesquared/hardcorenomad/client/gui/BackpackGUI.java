package net.firesquared.hardcorenomad.client.gui;

import java.util.ArrayList;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.client.gui.DynGUIBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class BackpackGUI extends DynGUIBase<BackpackContainer>
{
	static final int rowStart = 28, columnStart = 7, size = 18;
	private final static int scrollThreshold = 9, scrollElements = scrollThreshold - 2;
	final boolean isPlaced;
	boolean useScrolling;
	private int scrollIndex = 0;
	private int startX, startY;

	public BackpackGUI(BackpackContainer container)
	{
		super(container);
		this.isPlaced = container.isPlaced;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int i = 0;
		ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
		buttons.add(new GuiButton(-1, this.guiLeft + columnStart + 6 * 18, this.guiTop + 7, size, size, "U"));
		buttons.add(new GuiButton(100, this.guiLeft + columnStart, this.guiTop + 7, size * 3, size, "Dep All"));
		buttons.add(new GuiButton(101,this.guiLeft + columnStart + size * 3, this.guiTop + 7, size * 3, size, "Rec All"));
		int compCount = container.upgradeDisplaySlots.size();
		useScrolling = compCount > scrollThreshold;
		if(useScrolling)
		{
			//remove the GUI element showing the background for the slots that are going to be hidden
			Slot first = container.upgradeDisplaySlots.get(0);
			int index = first.slotNumber + 1 + scrollElements;
			for(int j = 8; j < container.upgradeDisplaySlots.size(); j++)
				elements.remove(index);
			elements.remove(first.slotNumber);
			for(int j = 0; j < scrollElements; j++)
				buttons.add(new GuiButton(i, this.guiLeft + columnStart + ++i*18, this.guiTop + rowStart, size, size + 1, "Tog"));
			startX = first.xDisplayPosition + 18;
			startY = first.yDisplayPosition;
			buttons.add(new GuiButton(-2, this.guiLeft + columnStart, this.guiTop + rowStart, size, 2 * size + 1, "<"));
			buttons.add(new GuiButton(-3, this.guiLeft + columnStart, this.guiTop + rowStart + 9 * 18, size, 2 * size + 1, ">"));
		}
		else
			for(;i < compCount;)
				buttons.add(new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i));
		
		for(GuiButton b : buttons)
		{
			b.enabled = true;
			buttonList.add(b);
		}
	}
	
	private void shiftScroll()
	{
		Slot s;
		int i, j;
		for(i = 0; i < container.upgradeDisplaySlots.size(); i++)
		{
			s = container.upgradeDisplaySlots.get(i);
			if(i < scrollIndex || i >= scrollIndex + scrollElements)
			{
				s.xDisplayPosition = -1000;
			}
			else
			{
				j = i - scrollIndex;
				s.xDisplayPosition = startX + j * 18;
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		//if using scrolling and the player clicks an arrow
		if(useScrolling && (button.id == -2 || button.id == -3))
		{
			if(button.id == -2)
				scrollIndex--;
			else
				scrollIndex++;
			scrollIndex%=container.upgradeDisplaySlots.size();
			shiftScroll();
		}
		
		if(isPlaced)
		{
			TileEntityBackPack te = ((TileEntityBackPack)container.backPack);
			int id = button.id;
			//if necessary, translate the button's index into an inventory index
			if(useScrolling && scrollIndex != 0 && id >= 0 && id < scrollElements)
				id = (id + scrollIndex) % scrollElements;
			Helper.PACKET_HANDLER.sendToServer(new ButtonPacket(te.xCoord, te.yCoord, te.zCoord, button.id));
		}
	}
}
