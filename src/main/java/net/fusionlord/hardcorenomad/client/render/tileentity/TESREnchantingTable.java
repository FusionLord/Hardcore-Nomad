package net.fusionlord.hardcorenomad.client.render.tileentity;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityEnchantingTable;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

import java.util.function.Function;

public class TESREnchantingTable extends TESRWRMRL<TileEntityEnchantingTable>
{
	private OBJModel.OBJBakedModel bookModel;
	private OBJModel.OBJBakedModel ringModel;

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		texture = new ResourceLocation(ModInfo.ID, "blocks/enchanter/enchanter");
		Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		try
		{
			OBJModel model;
			model = (OBJModel) OBJLoader.INSTANCE.loadModel(new ResourceLocation(ModInfo.ID, "models/block/enchanter/book.obj"));
			bookModel = (OBJModel.OBJBakedModel) model.bake(model.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetter::apply);
			model = (OBJModel) OBJLoader.INSTANCE.loadModel(new ResourceLocation(ModInfo.ID, "models/block/enchanter/ring.obj"));
			ringModel = (OBJModel.OBJBakedModel) model.bake(model.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetter::apply);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntityEnchantingTable table, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5f, y + 1, z + .5f);
		bindTexture(TextureMap.locationBlocksTexture);
		renderBook(table, partialTicks);
		if (table.getUpgrade() == EnumUpgrade.DIAMOND)
		{
			renderRing(table, partialTicks);
		}
		GlStateManager.popMatrix();
	}

	private void renderBook(TileEntityEnchantingTable table, float partialTicks)
	{
		GlStateManager.pushMatrix();
		float f = (float)table.tickCount / 2f + partialTicks;
		GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
		float f1;

		for (f1 = table.bookRotation - table.prevBookRotation; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F))
		{
			;
		}

		while (f1 < -(float)Math.PI)
		{
			f1 += ((float)Math.PI * 2F);
		}

		float f2 = table.prevBookRotation + f1 * partialTicks;
		GlStateManager.rotate(-f2 * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);

		GlStateManager.enableCull();
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.translate(0, -0.0625, 0);
		if (table.getUpgrade() == EnumUpgrade.DIAMOND) GlStateManager.translate(.15, 0, 0);
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightnessColor(bookModel, 1, 1, 1, 1);
		GlStateManager.popMatrix();
	}

	private void renderRing(TileEntityEnchantingTable table, float partialTicks)
	{
		GlStateManager.pushMatrix();
		float f = (float)table.tickCount / 2f + partialTicks;
		GlStateManager.translate(0.0F, 0.01F + MathHelper.sin(f * 0.3F) * 0.01F, 0.0F);
		GlStateManager.rotate(table.tickCount / 2f % 360, 0, 1, 0);
		GlStateManager.enableCull();
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightnessColor(ringModel, 1, 1, 1, 1);
		GlStateManager.popMatrix();
	}
}
