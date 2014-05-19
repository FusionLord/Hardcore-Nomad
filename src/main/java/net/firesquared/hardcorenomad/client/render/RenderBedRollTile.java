package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderBedRollTile extends TileEntitySpecialRenderer
{
	public static final IModelCustom
			modelBed = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/bedroll/bedroll.obj")),
			modelMat = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/bedroll/matting.obj")),
			modelPillow = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/bedroll/pillow.obj"));

	public static final ResourceLocation
			textureBed = new ResourceLocation("hardcorenomad:models/bedroll/bedroll.png"),
			textureMat = new ResourceLocation("hardcorenomad:models/bedroll/matting.png"),
			texturePillow = new ResourceLocation("hardcorenomad:models/bedroll/pillow.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x+.5, y, z+.5);
		int i = Blocks.BLOCK_BEDROLL.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);
		GL11.glScaled(.25, .25, .25);
		
		renderBed();
		renderMat();
		renderPillow();

		GL11.glPopMatrix();
	}

	private void renderBed()
	{
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureBed);
		
		GL11.glTranslated(0, .3, 0);
		//transform/scale/rotate

		modelBed.renderAll();
		
		GL11.glTranslated(0, -.3, 0);
	}
	
	private void renderMat()
	{
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureMat);
		
		//transform/scale/rotate

		modelMat.renderAll();
	}
	
	private void renderPillow()
	{
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texturePillow);
		
		GL11.glTranslated(3, .75, 0);
		//transform/scale/rotate

		modelPillow.renderAll();
		
		GL11.glTranslated(0, .25, 0);
		
		modelPillow.renderAll();
		
		GL11.glTranslated(-3, -1, 0);
	}
}
