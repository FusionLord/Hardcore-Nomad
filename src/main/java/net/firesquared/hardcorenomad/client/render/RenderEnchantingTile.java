package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEnchantingTile extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		TileEntityDeployableBase tileEntity = (TileEntityDeployableBase)te;

		IModelCustom model = ModelRegistry.getModel(Models.MODEL_ENCHANTING_TABLE, tileEntity.getTierLevel());
		ResourceLocation texture = ModelRegistry.getTexture(Models.MODEL_ENCHANTING_TABLE);

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslated(x, y, z);
		
		GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		GL11.glScalef(.25f, .25f, .25f);
		
		int i = Blocks.BLOCK_ENCHANTMENTTABLE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);
		
		model.renderAll();
		GL11.glPopMatrix();
	}
	
}
