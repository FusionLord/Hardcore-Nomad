

package net.firesquared.hardcorenomad.client.render.backpack;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderBackPackItem implements IItemRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		model = ModelRegistry.getModel(Models.BACKPACK);
		texture = ModelRegistry.getTexture(Models.BACKPACK, item.getItemDamage());
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glScalef(.44f, .44f, .44f);
		GL11.glTranslatef(0, -5.5f, 0);
		GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
		GL11.glColor4f(1, 1, 1, 1);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
