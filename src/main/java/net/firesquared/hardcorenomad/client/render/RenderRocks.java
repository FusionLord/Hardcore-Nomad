package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderRocks implements IItemRenderer
{
	public static final IModelCustom[] model = new IModelCustom[]{
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock1.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock2.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock3.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock4.obj")),
	};
	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/campfire/logrocks.png");


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
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glScalef(.44f, .44f, .44f);
		GL11.glTranslatef(0, 0.2f, 0);
		GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

		GL11.glColor4f(1, 1, 1, 1);

		model[0].renderAll();
		GL11.glPopMatrix();
	}
}
