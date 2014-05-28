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

		switch (ut)
		{
			case BREWING_STAND:
			case CRAFTING_TABLE:
			case STORAGE:
				return;
			case ANVIL:
				RenderCampComp.anvil.renderItem(type, copy, data);
				return;
			case COBBLE_GENERATOR:
				RenderCampComp.cobblegen.renderItem(type, copy, data);
				return;
			case BEDROLL:
				RenderCampComp.bedroll.renderItem(type, copy, data);
				return;
			case CAMPFIRE:
				RenderCampComp.campfire.renderItem(type, copy, data);
				return;
			case ENCHANTING_TABLE:
				RenderCampComp.enchanting.renderItem(type, copy, data);
				return;
			case BACKPACK:
				RenderCampComp.backpack.renderItem(type, copy, data);
				return;
			default:
				Helper.getLogger().error("Attempting to render an upgrade(" + itemStack.getDisplayName() + ") with no render code in RenderUpgradeItem");
				return;
		}
	}

	public static void renderNumeral(int damage)
	{
		GL11.glScalef(-.09f, -.09f, -.09f);
		GL11.glRotatef(225, 0f, 1f, 0f);
		GL11.glTranslatef(0f, 0f, 20f);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Numeral.ToRoman(damage + 1), -9, -20, 0xFFFF00);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor3f(1f, 1f, 1f);
	}
}
