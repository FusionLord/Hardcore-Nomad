package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
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
		GL11.glTranslatef(0.5f, 0f, 0.5f);
		GL11.glScalef(.4f, .4f, .4f);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
