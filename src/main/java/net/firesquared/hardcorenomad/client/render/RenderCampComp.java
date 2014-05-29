package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPack;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderAnvil;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBedRoll;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampfire;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCobbleGen;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCrafting;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchanting;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;

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

		int i = Blocks.BLOCK_ANVIL.block.getLightValue(te.getWorldObj(), (int)x, (int)y, (int)z);
		Tessellator.instance.setColorOpaque_F(i, i, i);

		renderTile((TileEntityDeployableBase)te, te.getWorldObj().getBlockLightOpacity((int)x, (int)y, (int)z));
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		renderItem(type, item);
		GL11.glPopMatrix();
		if (item.getItem() instanceof ItemBackPack || item.getItem() == ItemBlock.getItemFromBlock(Blocks.BLOCK_BACKPACK.block))
			return;

		UpgradeType ut = ItemUpgrade.getTypeFromDamage(item.getItemDamage());

		if (type == ItemRenderType.INVENTORY)
			RenderUpgradeItem.renderNumeral(item.getItemDamage() + (ut == UpgradeType.BACKPACK ? 2 : 0));
	}

	public abstract void renderItem(ItemRenderType type, ItemStack item);

	protected abstract void renderTile(TileEntityDeployableBase tile, int lighting);
	
}
