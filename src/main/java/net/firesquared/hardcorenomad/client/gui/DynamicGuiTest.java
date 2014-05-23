package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.client.gui.DynGUIBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;

public class DynamicGuiTest extends DynGUIBase<Container>
{
	static final int rowStart = 28, columnStart = 7, size = 18;
	final BackpackContainer container;
	final boolean isPlaced;

	public DynamicGuiTest(BackpackContainer container)
	{
		super(container);
		this.container = container;
		this.isPlaced = container.isPlaced;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int i = 0;
		GuiButton[] buttons = new GuiButton[]
				{
						new GuiButton(-1, this.guiLeft + columnStart + 8 * 18, this.guiTop + rowStart, size, size + 1, "U"),
						new GuiButton(100, this.guiLeft + columnStart, this.guiTop + 7, size * 3, size, "Deploy all"),
						new GuiButton(101,this.guiLeft + columnStart + size * 3, this.guiTop + 7, size * 4, size + 1, "Recover all"),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i),
						new GuiButton(i, this.guiLeft + columnStart + i++*18, this.guiTop + rowStart, size, size + 1, ""+i)
				};
		for(GuiButton b : buttons)
		{
			b.enabled = true;
			buttonList.add(b);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(isPlaced)
		{
			TileEntityBackPack te = ((TileEntityBackPack)container.backPack);
			Helper.PACKET_HANDLER.sendToServer(new ButtonPacket(te.xCoord, te.yCoord, te.zCoord, button.id));
		}
		
		super.actionPerformed(button);
	}
}
