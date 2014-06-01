package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.firesquared.hardcorenomad.block.BlockCampComponent;

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

	@SuppressWarnings("null")
	@Override
	public final void renderItem(ItemRenderType type, ItemStack is, Object... data)
	{
		int dmg = is.getItemDamage();
		Item item = is.getItem();
		UpgradeType ut = null;
		Block block = Block.getBlockFromItem(item);
		assert(item instanceof ItemUpgrade || item instanceof ItemBackPack || 
				block instanceof BlockCampComponent);
		
		
		if(item instanceof ItemUpgrade)
		{
			ut = ItemUpgrade.getTypeFromDamage(dmg);
			dmg = ItemUpgrade.getLevelFromDamage(dmg);
			if(ut == UpgradeType.BACKPACK)
				dmg++;
		}
		else if(block instanceof BlockCampComponent)
			ut = ((BlockCampComponent)block).getUpgradeType();
		
		GL11.glPushMatrix();
		renderItem(type, dmg);
		GL11.glPopMatrix();
		
		if(item instanceof ItemBackPack)
			return;
		if (type == ItemRenderType.INVENTORY)
			RenderUpgradeItem.renderNumeral(dmg, !ut.isUpgradeSequential);
	}

	public abstract void renderItem(ItemRenderType type, int lvl);

	protected abstract void renderTile(TileEntityDeployableBase tile, int lighting);
	
}
