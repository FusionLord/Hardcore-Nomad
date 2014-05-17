package net.firesquared.hardcorenomad.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderBackPackArmor extends ModelBiped
{
	public static final IModelCustom Model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/backpack/Backpack.obj"));

	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/backpack/backpack.png");
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslatef(0, 2.40f, .25f);
		GL11.glScalef(.35f, .35f, .35f);
		GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
		Model.renderAll();
		GL11.glPopMatrix();
	}
}
