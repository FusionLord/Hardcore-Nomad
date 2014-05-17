package net.firesquared.hardcorenomad.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.Blocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class RenderInchantingItem implements IItemRenderer
{
	
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
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		int level = 0;//get the level of the table form the TE
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(RenderEnchantingTile.texture);
		
		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);
		
		//Tessellator.instance.setColorOpaque_F(1,1,1);
		
		RenderEnchantingTile.model[level].renderAll();
		GL11.glPopMatrix();
	}
	
}
