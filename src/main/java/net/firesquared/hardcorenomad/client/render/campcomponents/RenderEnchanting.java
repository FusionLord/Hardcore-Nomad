package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;

import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.client.render.RenderCampComp;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

public class RenderEnchanting extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, tile.getCurrentLevel());
		texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);
		bindTexture(texture);
		
		Tessellator.instance.setColorOpaque_I(lighting);GL11.glTranslatef(0.5f, 0.65f, 0.5f);
		
		GL11.glScalef(.25f, .25f, .25f);
		model.renderAll();

	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item)
	{
		model = ModelRegistry.getModel(Models.ENCHANTINGTABLE, item.getItemDamage());
		texture = ModelRegistry.getTexture(Models.ENCHANTINGTABLE);
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
}
