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
		model = ModelRegistry.getModel(Models.CAMPFIRE);
		texture = ModelRegistry.getTexture(Models.CAMPFIRE);
		bindTexture(texture);
		GL11.glTranslatef(.5f, .5f, .5f);
		GL11.glScalef(.3f, .3f, .3f);
		model.renderAll();
		addRocks();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		model = ModelRegistry.getModel(Models.CAMPFIRE, 0);
		texture = ModelRegistry.getTexture(Models.CAMPFIRE);
		bindTexture(texture);

		GL11.glScalef(.25f, .25f, .25f);
		GL11.glTranslatef(0, .75f, 0);
		model.renderAll();
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
	
}
