package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderAnvil extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		renderAnvil();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		renderAnvil();
	}

	private void renderAnvil()
	{
		model = ModelRegistry.getModel(Models.ANVIL);
		texture = ModelRegistry.getTexture(Models.ANVIL);

		bindTexture(texture);

		GL11.glTranslatef(.5f, 0f, .5f);
		model.renderAll();
	}
}
