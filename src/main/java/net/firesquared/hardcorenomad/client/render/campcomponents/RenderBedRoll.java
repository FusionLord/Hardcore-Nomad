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
		render(tile.getCurrentLevel());
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item)
	{
		GL11.glRotatef(180, 0f, 1f, 0f);
		GL11.glTranslatef(0f, -.25f, .5f);
		GL11.glScalef(.85f, .85f, .85f);
		render(item.getItemDamage());
	}

	private void render(int damage)
	{
		GL11.glPushMatrix();
		GL11.glScalef(.18f, .18f, .18f);
		model = ModelRegistry.getModel(Models.BEDROLL);
		bindTexture(ModelRegistry.getTexture(Models.BEDROLL));
		GL11.glTranslated(0, .3, 0);
		model.renderAll();
		GL11.glTranslated(0, -.3, 0);
		if (damage >= 1)
		{
			model = ModelRegistry.getModel(Models.BEDROLL_MATTING);
			bindTexture(ModelRegistry.getTexture(Models.BEDROLL_MATTING));
			model.renderAll();
		}
		if (damage >= 2)
		{
			model = ModelRegistry.getModel(Models.BEDROLL_PILLOW);
			bindTexture(ModelRegistry.getTexture(Models.BEDROLL_PILLOW));
			GL11.glTranslated(3, .75, 0);
			model.renderAll();
			GL11.glTranslated(0, .25, 0);
			model.renderAll();
		}
		GL11.glPopMatrix();
		if (damage == 2)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glRotatef(180, 0f, 1f, 0f);
			GL11.glTranslatef(.15f, 0.34375f, 0f);
			GL11.glScalef(.18f, .18f, .18f);
			model = ModelRegistry.getModel(Models.BEDROLL_LEANTO);
			bindTexture(ModelRegistry.getTexture(Models.BEDROLL_LEANTO));
			model.renderAll();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();
		}
		if (damage == 3)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glRotatef(180, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0.34375f, 0f);
			GL11.glScalef(.18f, .18f, .18f);
			model = ModelRegistry.getModel(Models.BEDROLL_TENT);
			bindTexture(ModelRegistry.getTexture(Models.BEDROLL_TENT));
			model.renderAll();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();
		}
	}
}
