package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderRocksThrown extends Render
{
	public static final IModelCustom[] model = new IModelCustom[]{
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock1.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock2.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock3.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock4.obj")),
	};
	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/campfire/logrocks.png");

	public RenderRocksThrown() {

	}

	@Override public void doRender(Entity par1, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glRotatef(par1.prevRotationYaw + (par1.rotationYaw - par1.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(par1.prevRotationPitch + (par1.rotationPitch - par1.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);

		//GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		GL11.glScalef(.25f, .25f, .25f);

		Tessellator.instance.setColorOpaque_F(1, 1, 1);

		model[0].renderAll();
		GL11.glPopMatrix();
	}

	@Override protected ResourceLocation getEntityTexture(Entity var1)
	{
		return texture;
	}
}
