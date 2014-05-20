package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEnchantingTile extends TileEntitySpecialRenderer
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		TileEntityDeployableBase tileEntity = (TileEntityDeployableBase)te;

		model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, tileEntity.getCurrentLevel());
		texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);
		bindTexture(texture);

		int i = Blocks.BLOCK_ENCHANTMENTTABLE.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		GL11.glScalef(.25f, .25f, .25f);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
}
