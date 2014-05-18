package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

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

	@Override public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		//GL11.glScalef(.44f, .44f, .44f);
		//GL11.glTranslatef(0, -1, 0);
		//GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

		//GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glColor4f(1, 1, 1, 1);
		//Tessellator.instance.setColorOpaque_F(1, 1, 1);
		//Tessellator.instance.setBrightness(255);

		model[0].renderAll();
		GL11.glPopMatrix();
	}

	@Override protected ResourceLocation getEntityTexture(Entity var1)
	{
		return texture;
	}
}
