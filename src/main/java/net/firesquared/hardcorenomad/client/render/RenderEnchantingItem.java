package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.item.ItemNomadBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderEnchantingItem implements IItemRenderer
{
	IModelCustom model;
	ResourceLocation texture;

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
		model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, ItemNomadBase.getTierLevel(item));
		texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		
		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);

		model.renderAll();
		GL11.glPopMatrix();
	}
	
}
