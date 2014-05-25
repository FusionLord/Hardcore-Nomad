package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPack;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public abstract class RenderCampComp extends TileEntitySpecialRenderer implements IItemRenderer
{
	public static RenderCampComp backpack = new RenderBackPack();
	public static RenderCampComp campfire = new RenderCampfire();
	public static RenderCampComp bedroll = new RenderBedRoll();
	public static RenderCampComp enchanting = new RenderEnchanting();

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

		int i = Blocks.BLOCK_BEDROLL.getBlock().getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		renderTile((TileEntityDeployableBase)te, te.getWorldObj().getBlockLightOpacity((int)x, (int)y, (int)z));
		
		GL11.glPopMatrix();
	}
	
	protected abstract void renderTile(TileEntityDeployableBase tile, int lighting);
	
}
