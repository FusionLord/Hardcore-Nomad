package net.firesquared.hardcorenomad.client.gui;

import java.util.ArrayList;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.client.gui.DynamicGUIContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Slot;

public class BackpackGUI extends DynamicGUIContainer<BackpackContainer>
{
	static final int rowStart = 28, columnStart = 7, eleXSize = 18, eleYSize = 20;
	private final static int scrollThreshold = 9, scrollElements = scrollThreshold - 2;
	final boolean isPlaced;
	boolean useScrolling;
	private int scrollIndex = 0;
	private int startX;

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
		buttons.add(new GuiButton(-1,  this.guiLeft + columnStart + 6 * 18,		this.guiTop + 6, eleXSize, eleYSize, "U"));
		buttons.add(new GuiButton(100, this.guiLeft + columnStart, 				this.guiTop + 6, eleXSize * 3, eleYSize, "Dep All"));
		buttons.add(new GuiButton(101, this.guiLeft + columnStart + eleXSize * 3, 	this.guiTop + 6, eleXSize * 3, eleYSize, "Rec All"));
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
				buttons.add(new GuiButton(i, this.guiLeft + columnStart + ++i*18, this.guiTop + rowStart, eleXSize, eleYSize, "Tog"));
			startX = first.xDisplayPosition + 18;
			buttons.add(new GuiButton(-2, this.guiLeft + columnStart, this.guiTop + rowStart, eleXSize, 2 * eleYSize, "<"));
			buttons.add(new GuiButton(-3, this.guiLeft + columnStart, this.guiTop + rowStart + 9 * 18, eleXSize, 2 * eleYSize, ">"));
		}
		else
			for(;i < compCount;)
				buttons.add(new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, eleXSize, eleYSize, ""+i));
		
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
			if(id==-1)
				container.detectAndSendChanges();
				
		}
	}
}
