package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderCampfireTile extends TileEntitySpecialRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		model = ModelRegistry.getModel(Models.CAMPFIRE);
		texture = ModelRegistry.getTexture(Models.CAMPFIRE);
		bindTexture(texture);

		int i = Blocks.BLOCK_CAMPFIRE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(.5f, .5f, .5f);
		GL11.glScalef(.3f, .3f, .3f);
		model.renderAll();
		addRocks(); // addRandom
		GL11.glPopMatrix();
	}

	private void addRocks()
	{
		GL11.glTranslatef(0f, -1.5f, 0f);
		for (int i = 0; i < 8; i++)
		{
			model = ModelRegistry.getModel(Models.ROCK, i % Models.ROCK.modelCount);
			GL11.glRotatef(45, 0f, (float)i * 1f, 0f);
			GL11.glTranslatef(0f, 0f, 2.5f);
			model.renderAll();
			GL11.glTranslatef(0f, 0f, -2.5f);
		}
	}
}
