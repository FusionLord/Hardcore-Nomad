package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.block.BlockBedRoll;
import net.firesquared.hardcorenomad.block.BlockCampFire;
import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderUpgradeItem implements IItemRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;
	FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	float scale = 0, xOffset = 0, yOffest = 0, zOffset = 0;
	boolean needsRotate;
	int rotDegree;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,	ItemRendererHelper helper)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
	{
		ItemUpgrade item = (ItemUpgrade)itemStack.getItem();

		if (item.getUpgradeTarget() == BlockEnchantmentTable.class)
		{
			model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, item.getTargetLevel());
			texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);

			scale = .25f;
			yOffest = .5f;
		}

		if (item.getUpgradeTarget() == BlockCampFire.class)
		{
			model = ModelRegistry.getModel(Models.CAMPFIRE);
			texture = ModelRegistry.getTexture(Models.CAMPFIRE);

			scale = .25f;
		}

		if (item.getUpgradeTarget() == BlockBedRoll.class)
		{
			model = ModelRegistry.getModel(Models.BEDROLL);
			texture = ModelRegistry.getTexture(Models.BEDROLL);

			scale = .20f;
			needsRotate = true;
			rotDegree = 180;
		}

		if(model == null)
		{
			LogHelper.fatal("How did you even? Cannot render model.");
			return;
		}

		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();

		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(xOffset, yOffest, zOffset);

		if (needsRotate)
		{
			GL11.glRotatef(rotDegree, 0f, 1f, 0f);
		}

		model.renderAll();

		if (item.getUpgradeTarget() == BlockBedRoll.class)
		{
			renderBedrollExtras();
		}

		GL11.glPopMatrix();
		GL11.glPushMatrix();

		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glScalef(-.05f, -.05f, -.05f);
			GL11.glRotatef(225, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0f, 20f);
			RenderHelper.disableStandardItemLighting();
			font.drawString(Integer.toString(item.getTargetLevel() + 1), -12, -25, 0xFFFF00);
			RenderHelper.enableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
		}

		GL11.glPopMatrix();

	}

	private void renderBedrollExtras()
	{
		model = ModelRegistry.getModel(Models.BEDROLL_MATTING);
		texture = ModelRegistry.getTexture(Models.BEDROLL_MATTING);
		ModelRegistry.bindTexture(texture);
		GL11.glTranslated(0, -.3, 0);
		model.renderAll();

		model = ModelRegistry.getModel(Models.BEDROLL_PILLOW);
		texture = ModelRegistry.getTexture(Models.BEDROLL_PILLOW);
		ModelRegistry.bindTexture(texture);
		GL11.glTranslated(3, .75, 0);
		model.renderAll();
		GL11.glTranslated(0, .25, 0);
		model.renderAll();
	}

}
