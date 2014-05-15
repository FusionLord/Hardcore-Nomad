


package net.firesquared.hardcorenomad.client.render;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLSync;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBackPack implements IItemRenderer
{
	IModelCustom Model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/Backpack.obj"));
	//ResourceLocation texture = new ResourceLocation("");// put the texture for
														// the backpack here
	
	
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
		GL11.glPushMatrix();
		//FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glScalef(.5f, .5f, .5f);
		GL11.glTranslatef(0, -5.5f, 0);
		Model.renderAll();
		GL11.glPopMatrix();
	}
}
