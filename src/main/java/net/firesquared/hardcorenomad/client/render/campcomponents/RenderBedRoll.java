package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderBedRoll extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		GL11.glTranslatef(.5f, 0f, .5f);
		render();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glRotatef(180, 0f, 1f, 0f);
		render();
	}

	private void render()
	{
		GL11.glScalef(.18f, .18f, .18f);
		model = ModelRegistry.getModel(Models.BEDROLL);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL));
		GL11.glTranslated(0, .3, 0);
		model.renderAll();
		GL11.glTranslated(0, -.3, 0);
		model = ModelRegistry.getModel(Models.BEDROLL_MATTING);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL_MATTING));
		model.renderAll();
		model = ModelRegistry.getModel(Models.BEDROLL_PILLOW);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL_PILLOW));
		GL11.glTranslated(3, .75, 0);
		model.renderAll();
		GL11.glTranslated(0, .25, 0);
		model.renderAll();
	}
}
