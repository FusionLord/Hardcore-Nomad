package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampComp;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquaredcore.helper.Helper.Numeral;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderUpgradeItem implements IItemRenderer
{
	FontRenderer font = Minecraft.getMinecraft().fontRenderer;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
	{
		int upgradeLevel = ItemUpgrade.getLevelFromDamage(itemStack.getItemDamage());
		UpgradeType ut = ItemUpgrade.getTypeFromDamage(itemStack.getItemDamage());

		ItemStack copy = itemStack.copy();
		copy.setItemDamage(upgradeLevel);

		GL11.glPushMatrix();
		switch (ut)
		{
			case BREWING_STAND:
			case CRAFTING_TABLE:
			case STORAGE:
				break;
			case ANVIL:
				RenderCampComp.anvil.renderItem(type, copy, data);
				break;
			case COBBLE_GENERATOR:
				RenderCampComp.cobblegen.renderItem(type, copy, data);
				break;
			case BEDROLL:
				RenderCampComp.bedroll.renderItem(type, copy, data);
				break;
			case CAMPFIRE:
				RenderCampComp.campfire.renderItem(type, copy, data);
				break;
			case ENCHANTING_TABLE:
				RenderCampComp.enchanting.renderItem(type, copy, data);
				break;
			case BACKPACK:
				RenderCampComp.backpack.renderItem(type, copy, data);
				break;
			default:
				Helper.getLogger().error("Attempting to render an upgrade(" + itemStack.getDisplayName() + ") with no render code in RenderUpgradeItem");
				return;
		}
		GL11.glPopMatrix();

		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glScalef(-.09f, -.09f, -.09f);
			GL11.glRotatef(225, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0f, 20f);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			font.drawStringWithShadow(Numeral.ToRoman(upgradeLevel + (ut == UpgradeType.BACKPACK ? 2 : 1)), -9, -20, 0xFFFF00);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glColor3f(1f, 1f, 1f);
		}
	}
}
