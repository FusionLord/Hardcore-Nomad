package net.firesquared.hardcorenomad.client.gui;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.network.SetOffsetPacket;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import java.util.List;
import java.util.ArrayList;

public class GuiAssignOffest extends GuiScreen
{
	GuiTextField xOffset;
	GuiTextField zOffset;
	List<String> errors = new ArrayList<String>();
	String error;

	TileEntityBackPack backPack;
	int componentIndex;

	public GuiAssignOffest(TileEntityBackPack backPack, int componentIndex)
	{
		this.backPack = backPack;
		this.componentIndex = componentIndex;
	}

	@Override
	public void initGui()
	{
		xOffset = new GuiTextField(this.fontRendererObj, 100, 80, 100, 20);
		zOffset = new GuiTextField(this.fontRendererObj, 200, 80, 100, 20);
		this.buttonList.add(new GuiButton(0, 100, 100, 100, 20, "Ok"));
		this.buttonList.add(new GuiButton(1, 200, 100, 100, 20, "Cancel"));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{

		switch (button.id)
		{
			case 0:
				errors.clear();

				int offsetX = -100;
				int offsetZ = -100;

				if (!isInteger(xOffset.getText()))
					errors.add("X-Offset isn't a number");
				else
					offsetX = Integer.parseInt(xOffset.getText());
				if (!isInteger(zOffset.getText()))
					errors.add("Y-Offset isn't a number");
				else
					offsetZ = Integer.parseInt(zOffset.getText());

				if (10 >= offsetX || offsetX <= -10)
					errors.add("X-Offset needs to be between 10 and -10");

				if (10 >= offsetZ || offsetZ <= -10)
					errors.add("Z-Offset needs to be between 10 and -10");

				if (errors.size() != 0)
					return;

				Helper.PACKET_HANDLER.sendToServer(new SetOffsetPacket(backPack.xCoord, backPack.yCoord, backPack.zCoord, componentIndex, offsetX, offsetZ));

				break;
			case 1:
				this.mc.thePlayer.closeScreen();
				break;
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);

		for (int i = 0; i < errors.size(); i++)
			fontRendererObj.drawString(errors.get(i).concat((i == errors.size() - 1 ? "!" : ",")), fontRendererObj.getStringWidth(errors.get(i)) / 2 + 150, 60 - (fontRendererObj.FONT_HEIGHT * i), 0xFF0000);
	}
}
