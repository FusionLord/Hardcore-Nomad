package net.firesquared.hardcorenomad.client.render;

import codechicken.lib.render.BlockRenderer;
import net.firesquared.hardcorenomad.block.BlockBedRoll;
import net.firesquared.hardcorenomad.block.BlockCampFire;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.firesquared.hardcorenomad.item.upgrades.UpgradeType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderUpgradeItem implements IItemRenderer
{
	IModelCustom model = null;
	RenderBlocks renderBlocks;
	ResourceLocation texture = null;
	FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	TextureManager texmgr = Minecraft.getMinecraft().getTextureManager();
	float scale = 0, xOffset = 0, yOffest = 0, zOffset = 0;
	boolean needsRotate;
	int rotDegree;

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
		ItemUpgrade item = (ItemUpgrade)itemStack.getItem();
		int upgradeLevel = ItemUpgrade.getUpgradeLevel(itemStack.getItemDamage());

		if (type != ItemRenderType.FIRST_PERSON_MAP)
		{
			renderBlocks = (RenderBlocks) data[0];
		}

		GL11.glPushMatrix();

		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glScalef(-.09f, -.09f, -.09f);
			GL11.glRotatef(225, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0f, 20f);
			RenderHelper.disableStandardItemLighting();
			font.drawString(Helper.Numeral.ToRoman(upgradeLevel), -9, -20, 0xFFFF00);
			RenderHelper.enableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glColor3f(1f, 1f, 1f);
		}

		GL11.glPopMatrix();

		switch (item.getUpgradeType())
		{
			case ANVIL:
			case BREWINGSTAND:
			case COBBLEGEN:
			case CRAFTINGTABLE:
				return;
			case BEDROLL:
				model = ModelRegistry.getModel(Models.BEDROLL);
				texture = ModelRegistry.getTexture(Models.BEDROLL);

				scale = .18f;
				needsRotate = true;
				rotDegree = 90;
				break;
			case CAMPFIRE:
				model = ModelRegistry.getModel(Models.CAMPFIRE);
				texture = ModelRegistry.getTexture(Models.CAMPFIRE);

				scale = .25f;
				break;
			case ENCHANTINGTABLE:
				model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, upgradeLevel - 1);
				texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);

				scale = .25f;
				yOffest = .5f;
				break;
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

		if (item.getUpgradeType() == UpgradeType.BEDROLL)
		{
			renderBedrollExtras();
		}
		if (item.getUpgradeType() == UpgradeType.CAMPFIRE)
		{
			renderCampfireExtras();
		}

		GL11.glPopMatrix();
	}

	private void renderCampfireExtras()
	{
		GL11.glTranslatef(0f, -1.5f, 0f);
		for (int i = 0; i < 8; i++)
		{
			model = ModelRegistry.getModel(Models.ROCK, i % Models.ROCK.modelCount);
			GL11.glRotatef(45, 0f, (float)i * 1f, 0f);
			GL11.glTranslatef(0f, 0f, 2.5f);
			model.renderAll();
			GL11.glTranslatef(0f, 0f, -2.5f);
		}
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
