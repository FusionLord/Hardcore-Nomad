package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderRocksItem implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
	{
		return type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
	{
		IModelCustom model = ModelRegistry.getModel(Models.MODEL_ROCKS);
		ResourceLocation texture = ModelRegistry.getTexture(Models.MODEL_ROCKS);

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glScalef(.44f, .44f, .44f);
		GL11.glTranslatef(0, 0.2f, 0);
		GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

		GL11.glColor4f(1, 1, 1, 1);

		model.renderAll();
		GL11.glPopMatrix();
	}
}
