package net.firesquared.hardcorenomad.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBackPackArmor extends ModelBiped
{
	IModelCustom Model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/Backpack.obj"));

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		//FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslatef(0, -1.75f, .25f);
		GL11.glScalef(.35f, .35f, .35f);
		Model.renderAll();
		GL11.glPopMatrix();
	}
}
