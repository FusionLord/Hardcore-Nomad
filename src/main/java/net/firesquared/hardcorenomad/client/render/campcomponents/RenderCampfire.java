package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

public class RenderCampfire extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		int damage = tile.getCurrentLevel();
		if (damage == 2)
			renderSpit();
		setModelAndTexture(tile.getCurrentLevel());
		bindTexture(texture);
		GL11.glTranslatef(.5f, .25f, .5f);
		GL11.glScalef(.15f, .15f, .15f);
		model.renderAll();
		if (tile.getCurrentLevel() != 0 && tile.getCurrentLevel() != 3)
			addRocks();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		int damage = item.getItemDamage();
			if (damage == 2)
				renderSpit();
			setModelAndTexture(damage);
			bindTexture(texture);
			GL11.glTranslatef(.5f, .25f, .5f);
			GL11.glScalef(.15f, .15f, .15f);
			model.renderAll();
			if (damage != 0 && damage != 3)
				addRocks();
	}

	private void renderSpit()
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(.5f, .3625f, .5f);
		GL11.glScalef(.2f, .2f, .2f);
		model = ModelRegistry.getModel(Models.CAMPFIRE_SPIT);
		texture = ModelRegistry.getTexture(Models.CAMPFIRE_SPIT);
		bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();
	}

	private void addRocks()
	{
		GL11.glTranslatef(0f, -1.5f, 0f);
		for (int i = 0; i < 8; i++)
		{
			model = ModelRegistry.getModel(Models.ROCK, i % Models.ROCK.modelCount);
			GL11.glRotatef(45, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0f, 2.5f);
			model.renderAll();
			GL11.glTranslatef(0f, 0f, -2.5f);
		}
	}

	private void setModelAndTexture(int damage)
	{
		switch (damage)
		{
			case 0:
			case 1:
			case 2:
				model = ModelRegistry.getModel(Models.CAMPFIRE);
				texture = ModelRegistry.getTexture(Models.CAMPFIRE);
				return;
			case 3:
				model = ModelRegistry.getModel(Models.CAMPFIRE_OPENOVEN);
				texture = ModelRegistry.getTexture(Models.CAMPFIRE_OPENOVEN);
				return;
		}
	}
}
