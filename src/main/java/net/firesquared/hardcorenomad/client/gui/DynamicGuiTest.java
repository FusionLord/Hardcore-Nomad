package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.firesquaredcore.client.gui.DynGUIBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;

public class DynamicGuiTest extends DynGUIBase<Container>
{
	static final int rowStart = 28, columnStart = 7, size = 18;
	public GuiButton[] buttons;
	BackpackContainer container;
	private boolean isPlaced;

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
		buttons = new GuiButton[]
				{
						new GuiButton(0, this.guiLeft + columnStart + 8 * 18, this.guiTop + rowStart, size, size + 1, "U"),
						new GuiButton(1, this.guiLeft + columnStart, this.guiTop + 7, size * 3, size, "Deploy all"),
						new GuiButton(2,this.guiLeft + columnStart + size * 3, this.guiTop + 7, size * 4, size + 1, "Recover all"),
						new GuiButton(3, this.guiLeft + columnStart + 0*18, this.guiTop + rowStart, size, size + 1, "1"),
						new GuiButton(4, this.guiLeft + columnStart + 1*18, this.guiTop + rowStart, size, size + 1, "2"),
						new GuiButton(5, this.guiLeft + columnStart + 2*18, this.guiTop + rowStart, size, size + 1, "3"),
						new GuiButton(6, this.guiLeft + columnStart + 3*18, this.guiTop + rowStart, size, size + 1, "4"),
						new GuiButton(7, this.guiLeft + columnStart + 4*18, this.guiTop + rowStart, size, size + 1, "5"),
						new GuiButton(8, this.guiLeft + columnStart + 5*18, this.guiTop + rowStart, size, size + 1, "6"),
						new GuiButton(9, this.guiLeft + columnStart + 6*18, this.guiTop + rowStart, size, size + 1, "7"),
						new GuiButton(10, this.guiLeft + columnStart + 7*18, this.guiTop + rowStart, size, size + 1, "8"),
				};
		for(GuiButton b : buttons)
		{
			b.enabled = true;
			buttonList.add(b);
		}
	}
}
