package net.firesquared.hardcorenomad.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEnchantingTile extends TileEntitySpecialRenderer
{
	public static final IModelCustom[] model = new IModelCustom[]{
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/enchanting/ShelfTier1.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/enchanting/ShelfTier2.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/enchanting/ShelfTier3.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/enchanting/ShelfTier4.obj")),
		AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/enchanting/ShelfTier5.obj"))
	};
	public static final ResourceLocation texture = new ResourceLocation("hardcorenomad:models/enchanting/EnTTable.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		int level = 4;//get the level of the table form the TE
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslated(x, y, z);
		
		GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		GL11.glScalef(.25f, .25f, .25f);
		
		int i = Blocks.BLOCK_ENCHANTMENTTABLE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);
		
		model[level].renderAll();
		GL11.glPopMatrix();
	}
	
}
