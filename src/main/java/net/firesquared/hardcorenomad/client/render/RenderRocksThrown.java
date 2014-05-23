package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderRocksThrown extends Render
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	public RenderRocksThrown()
	{}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float pitch, float yaw)
	{
		model = ModelRegistry.getModel(Models.ROCK, entity.worldObj.rand.nextInt(Models.ROCK.modelCount));
		texture = ModelRegistry.getTexture(Models.ROCK);
		bindTexture(texture);

		Tessellator.instance.setColorOpaque_F(1, 1, 1);

		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y + .5f, (float)z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yaw - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * yaw, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(.25f, .25f, .25f);
		model.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1)
	{
		return null;
	}
}
