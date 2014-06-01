package net.firesquared.hardcorenomad.client.render.campcomponents;

import net.firesquared.hardcorenomad.client.render.RenderCampComp;
import net.firesquared.hardcorenomad.helpers.enums.Models;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCobbleGenerator;
import net.firesquaredcore.helper.ModelRegistry;
import net.firesquaredcore.helper.RenderingUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

public class RenderCobbleGen extends RenderCampComp
{
	IModelCustom vat;

	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{
		TileEntityCobbleGenerator cobbleGen = (TileEntityCobbleGenerator)tile;
		float percentage = cobbleGen.getPercentage();
		float rot = cobbleGen.getRotation();

		GL11.glTranslatef(.5f, 0f, .5f);
		vat = ModelRegistry.getModel(Models.COBBLEGEN_VAT);
		model = ModelRegistry.getModel(Models.COBBLEGEN);
		texture = ModelRegistry.getTexture(Models.COBBLEGEN);
		bindTexture(texture);
		model.renderOnly("trough", "outputvat");
		bindTexture(TextureMap.locationBlocksTexture);
		RenderingUtil.renderPartWithIcon((WavefrontObject) model, "output", Blocks.cobblestone.getIcon(0, 0),
				Tessellator.instance, -1);
		GL11.glPushMatrix();
		{
			if (percentage > .75f)
				percentage = .75f;
			GL11.glTranslatef(0f, percentage, 0f);
			bindTexture(texture);
			model.renderOnly("armlava", "armwater");
			GL11.glTranslatef(-.35f, .15f, -0.3375f);
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-rot, 0f, 0f, 1f);
				vat.renderPart("vat");
				bindTexture(TextureMap.locationBlocksTexture);
				RenderingUtil.renderPartWithIcon((WavefrontObject) vat, "contents", Blocks.lava.getIcon(0, 0),
						Tessellator.instance, -1);
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(.7f, 0f, 0f);
			GL11.glPushMatrix();
			{
				GL11.glRotatef(rot, 0f, 0f, 1f);
				bindTexture(texture);
				vat.renderPart("vat");
				bindTexture(TextureMap.locationBlocksTexture);
				RenderingUtil.renderPartWithIcon((WavefrontObject) vat, "contents", Blocks.water.getIcon(0, 0),
						Tessellator.instance, -1);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderItem(ItemRenderType type, int damage)
	{
		GL11.glTranslatef(0f, -.5f, 0f);
		model = ModelRegistry.getModel(Models.COBBLEGEN);
		texture = ModelRegistry.getTexture(Models.COBBLEGEN);
		bindTexture(texture);
		model.renderOnly("trough", "outputvat", "lavavat", "watervat");

		bindTexture(TextureMap.locationBlocksTexture);
		RenderingUtil
				.renderPartWithIcon((WavefrontObject) model, "lava", Blocks.lava.getIcon(0, 0), Tessellator.instance,
						-1);
		RenderingUtil
				.renderPartWithIcon((WavefrontObject) model, "water", Blocks.water.getIcon(0, 0), Tessellator.instance,
						-1);

		RenderingUtil.renderPartWithIcon((WavefrontObject) model, "output", Blocks.cobblestone.getIcon(0, 0),
				Tessellator.instance, -1);
	}
}
