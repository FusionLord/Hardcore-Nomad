package net.firesquared.hardcorenomad.client.render.campcomponents;

import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.model.IModelCustom;

public abstract class RenderCampComp extends TileEntitySpecialRenderer implements IItemRenderer
{
	protected IModelCustom model;
	protected ResourceLocation texture;
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY;
	}
	
	@Override
	public final void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		renderTile((TileEntityDeployableBase)te, te.getWorldObj().getBlockLightOpacity((int)x, (int)y, (int)z));
		
		GL11.glPopMatrix();
	}
	
	protected abstract void renderTile(TileEntityDeployableBase tile, int lighting);
	
}
