package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCobbleGenerator;
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
		TileEntityCobbleGenerator cobbleGen = (TileEntityCobbleGenerator)tile; 
		int time = cobbleGen.getCurrentProcessTime() + 1;
		GL11.glTranslatef(.5f, 0f, .5f);
		model = ModelRegistry.getModel(Models.COBBLEGEN);
		texture = ModelRegistry.getTexture(Models.COBBLEGEN);
		bindTexture(texture);
		model.renderOnly("trough", "outputvat");
		
		float height = time / 1000f;
		float rotation = (time < 900 ? 0 : 45f * (time / 50f));
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0f, height, 0f);
			model.renderPart("arm");
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-rotation, 0f, 0f, 1f);
				model.renderPart("lavavat");
				bindTexture(TextureMap.locationBlocksTexture);
				RendererUtil.renderPartWithIcon((WavefrontObject) model, "lava", Blocks.lava.getIcon(0, 0),
						Tessellator.instance, -1);
				GL11.glRotatef(rotation * 2, 0f, 0f, 1f);
				bindTexture(texture);
				model.renderPart("watervat");
				bindTexture(TextureMap.locationBlocksTexture);
				RendererUtil.renderPartWithIcon((WavefrontObject) model, "water", Blocks.water.getIcon(0, 0),
						Tessellator.instance, -1);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();

		RendererUtil.renderPartWithIcon((WavefrontObject) model, "output", Blocks.cobblestone.getIcon(0, 0),
				Tessellator.instance, -1);
		if (/* (TileEntityCobbleGen)tile.time > 100 && (TileEntityCobbleGen)tile.time < 130 */ true)
			RendererUtil.renderPartWithIcon((WavefrontObject)model, "cobble", Blocks.cobblestone.getIcon(0, 0), Tessellator.instance, -1);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glTranslatef(0f, -.5f, 0f);
		model = ModelRegistry.getModel(Models.COBBLEGEN);
		texture = ModelRegistry.getTexture(Models.COBBLEGEN);
		bindTexture(texture);
		model.renderOnly("trough", "outputvat", "lavavat", "watervat");

		bindTexture(TextureMap.locationBlocksTexture);
		RendererUtil.renderPartWithIcon((WavefrontObject)model, "lava", Blocks.lava.getIcon(0, 0), Tessellator.instance, -1);
		RendererUtil.renderPartWithIcon((WavefrontObject)model, "water", Blocks.water.getIcon(0, 0), Tessellator.instance, -1);

		RendererUtil.renderPartWithIcon((WavefrontObject) model, "output", Blocks.cobblestone.getIcon(0, 0), Tessellator.instance, -1);
	}
}
