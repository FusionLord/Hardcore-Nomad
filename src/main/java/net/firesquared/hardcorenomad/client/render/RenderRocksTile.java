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

public class RenderRocksTile extends TileEntitySpecialRenderer
{
	public static final IModelCustom[] model = new IModelCustom[]{
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock1.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock2.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock3.obj")),
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock4.obj")),
	};
	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/campfire/logrocks.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		int level = 0; //get the level of the table form the TE
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslated(x, y, z);

		GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		GL11.glScalef(.25f, .25f, .25f);

		int i = Blocks.BLOCK_CAMPFIRE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		model[level].renderAll();
		GL11.glPopMatrix();
	}
}