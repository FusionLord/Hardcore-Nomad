package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderBackPackTile extends TileEntitySpecialRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		TileEntityDeployableBase tileEntity = (TileEntityDeployableBase)te;

		model = ModelRegistry.getModel(Models.BACKPACK);
		texture = ModelRegistry.getTexture(Models.BACKPACK, tileEntity.getTierLevel());
		bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, -1.44f, 0.5f);
		GL11.glScalef(.35f, .35f, .35f);
		model.renderAll();
		GL11.glPopMatrix();
	}

}
