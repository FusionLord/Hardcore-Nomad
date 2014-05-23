package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderEnchantingItem implements IItemRenderer
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
		model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, ItemUpgrade.getLevelFromDamage(item.getItemDamage()));
		texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
}
