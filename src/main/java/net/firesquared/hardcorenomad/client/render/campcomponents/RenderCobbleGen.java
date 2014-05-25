package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.ModelRegistry;
import net.firesquaredcore.helper.RendererUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

public class RenderCobbleGen extends RenderCampComp
{
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		GL11.glTranslatef(.5f, 0f, .5f);
		render();
		if (/* (TileEntityCobbleGen)tile.time > 100 && (TileEntityCobbleGen)tile.time < 130 */ true)
			RendererUtil.renderPartWithIcon((WavefrontObject)model, "cobble", Blocks.cobblestone.getIcon(0, 0), Tessellator.instance, -1);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		render();
		RendererUtil.renderPartWithIcon((WavefrontObject) model, "cobble", Blocks.cobblestone.getIcon(0, 0),
				Tessellator.instance, -1);
	}

	private void render()
	{
		model = ModelRegistry.getModel(Models.COBBLEGEN);
		texture = ModelRegistry.getTexture(Models.COBBLEGEN);
		bindTexture(texture);
		model.renderPart("generator");

		bindTexture(TextureMap.locationBlocksTexture);
		RendererUtil.renderPartWithIcon((WavefrontObject)model, "lava", Blocks.lava.getIcon(0, 0), Tessellator.instance, -1);
		RendererUtil.renderPartWithIcon((WavefrontObject)model, "water", Blocks.water.getIcon(0, 0), Tessellator.instance, -1);

	}
}
