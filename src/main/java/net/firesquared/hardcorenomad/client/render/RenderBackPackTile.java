package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderBackPackTile extends TileEntitySpecialRenderer
{

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		TileEntityDeployableBase tileEntity = (TileEntityDeployableBase)te;

		IModelCustom model = ModelRegistry.getModel(Models.BACKPACK);
		ResourceLocation texture = ModelRegistry.getTexture(Models.BACKPACK, tileEntity.getTierLevel());

		//LogHelper.debug("Went to render, im told to render this... ==> " + backPackType);

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, -1.44f, 0.5f);
		GL11.glScalef(.35f, .35f, .35f);
		model.renderAll();
		GL11.glPopMatrix();
	}

}
