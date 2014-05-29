package net.firesquared.hardcorenomad.client.render.misc;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderSlingshotItem implements IItemRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		model = ModelRegistry.getModel(Models.SLINGSHOT);
		texture = ModelRegistry.getTexture(Models.SLINGSHOT);
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glScalef(.3f, .3f, .3f);
		GL11.glRotatef(180, 0f, 1f, 0f);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
