package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

public class RenderCampfire extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		setModelAndTexture(tile.getCurrentLevel());
		bindTexture(texture);
		GL11.glTranslatef(.5f, .5f, .5f);
		GL11.glScalef(.3f, .3f, .3f);
		model.renderAll();
		if (tile.getCurrentLevel() != 0 && tile.getCurrentLevel() != 4)
			addRocks();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		setModelAndTexture(item.getItemDamage());
		bindTexture(texture);

		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);
		model.renderAll();
		if (item.getItemDamage() != 0 && item.getItemDamage() != 4)
			addRocks();
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
				model = ModelRegistry.getModel(Models.CAMPFIRE);
				texture = ModelRegistry.getTexture(Models.CAMPFIRE);
				break;
			case 2:
			case 3:

				break;
			case 4:
				model = ModelRegistry.getModel(Models.CAMPFIRE_OPENOVEN);
				texture = ModelRegistry.getTexture(Models.CAMPFIRE_OPENOVEN);
				break;
		}
	}
}
