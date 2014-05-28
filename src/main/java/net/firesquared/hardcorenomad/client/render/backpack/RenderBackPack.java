

package net.firesquared.hardcorenomad.client.render.backpack;

import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampComp;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderBackPack extends RenderCampComp
{
	@Override
	public void renderItem(ItemRenderType type, ItemStack item)
	{
		render(item.getItemDamage() + (item.getItem() instanceof ItemUpgrade ? 1 : 0));
	}

	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		GL11.glScalef(.8f, .8f, .8f);
		GL11.glTranslatef(.625f, .5f, .625f);
		render(tile.getCurrentLevel());
	}

	protected void render(int damage)
	{
		model = ModelRegistry.getModel(Models.BACKPACK);
		texture = ModelRegistry.getTexture(Models.BACKPACK, damage);
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glScalef(.44f, .44f, .44f);
		GL11.glTranslatef(0, -5.5f, 0);
		GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
		GL11.glColor4f(1, 1, 1, 1);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
