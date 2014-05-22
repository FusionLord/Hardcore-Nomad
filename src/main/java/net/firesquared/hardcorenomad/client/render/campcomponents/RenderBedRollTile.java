package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.lib.helper.ModelRegistry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderBedRollTile extends TileEntitySpecialRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x+.5, y, z+.5);
		int i = Blocks.BLOCK_BEDROLL.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);
		GL11.glScaled(.25, .25, .25);
		
		renderBed();

		GL11.glPopMatrix();
	}

	private void renderBed()
	{
		model = ModelRegistry.getModel(Models.BEDROLL);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL));
		GL11.glTranslated(0, .3, 0);
		model.renderAll();
		GL11.glTranslated(0, -.3, 0);
		model = ModelRegistry.getModel(Models.BEDROLL_MATTING);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL_MATTING));
		model.renderAll();
		model = ModelRegistry.getModel(Models.BEDROLL_PILLOW);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL_PILLOW));
		GL11.glTranslated(3, .75, 0);
		model.renderAll();
		GL11.glTranslated(0, .25, 0);
		model.renderAll();
	}
}
