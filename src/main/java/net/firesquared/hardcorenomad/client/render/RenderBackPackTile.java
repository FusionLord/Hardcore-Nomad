package net.firesquared.hardcorenomad.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBackPackTile extends TileEntitySpecialRenderer
{
	IModelCustom Model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/Backpack.obj"));

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();
		//FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(""));
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, -1.5f, 0.5f);
		GL11.glScalef(.35f, .35f, .35f);
		Model.renderAll();
		GL11.glPopMatrix();
	}

}
