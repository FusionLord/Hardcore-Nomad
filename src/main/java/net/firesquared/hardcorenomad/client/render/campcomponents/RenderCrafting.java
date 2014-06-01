package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.client.render.RenderCampComp;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;
import net.firesquaredcore.helper.RenderingUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

public class RenderCrafting extends RenderCampComp
{
	@Override
	public void renderItem(ItemRenderType type, int dmg)
	{
		GL11.glTranslatef(0f, -.5f, 0f);
		render();
	}

	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		GL11.glTranslatef(.5f, 0f, .5f);
		render();
	}

	private void render()
	{
		model = ModelRegistry.getModel(Models.CUBE);
		bindTexture(TextureMap.locationBlocksTexture);
		for (int i = 0; i < 6; i++)
		{
			RenderingUtil.renderPartWithIcon((WavefrontObject)model, "side".concat(Integer.toString(i)),  net.minecraft.init.Blocks.crafting_table.getIcon(i, 0), Tessellator.instance, -1);
		}
	}
}
