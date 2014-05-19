package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
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
	public RenderRocksThrown()
	{}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float pitch, float yaw)
	{
		IModelCustom model = ModelRegistry.getModel(Models.ROCK, entity.worldObj.rand.nextInt(Models.ROCK.modelCount));
		ResourceLocation texture = ModelRegistry.getTexture(Models.ROCK);

		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yaw - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * yaw, 0.0F, 0.0F, 1.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);

		GL11.glScalef(.25f, .25f, .25f);

		Tessellator.instance.setColorOpaque_F(1, 1, 1);

		model.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1)
	{
		return null;
	}
}
