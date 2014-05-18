package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderCampfireTile extends TileEntitySpecialRenderer
{
	public static final IModelCustom modelLog = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/log.obj"));

	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/campfire/logrocks.png");
	
	public static final IModelCustom modelCombined = 
			AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/Campfire.obj"));
	
	public static final IModelCustom[] modelRock = new IModelCustom[]{
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock1.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock2.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock3.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/campfire/rock4.obj")),
};
public static final ResourceLocation textureRock = new ResourceLocation("hardcorenomad:models/campfire/logrocks.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		
		GL11.glTranslated(x, y, z);

		GL11.glTranslatef(0.5f, 0f, 0.5f);
		
		GL11.glScalef(.5f, .5f, .5f);

		int i = Blocks.BLOCK_CAMPFIRE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		modelCombined.renderAll();
		
		GL11.glPopMatrix();
	}
}
