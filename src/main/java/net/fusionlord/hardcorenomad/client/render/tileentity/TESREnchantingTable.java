package net.fusionlord.hardcorenomad.client.render.tileentity;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.common.tileentity.TileEntityEnchantingTable;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import java.util.function.Function;

public class TESREnchantingTable extends TESRWRMRL<TileEntityEnchantingTable>
{
	private OBJModel.OBJBakedModel bookModel;
	private OBJModel.OBJBakedModel ringModel;
	private final ResourceLocation texture = new ResourceLocation(ModInfo.ID, "textures/blocks/enchanter/enchanter");
	private final Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		OBJLoader.INSTANCE.addDomain(ModInfo.ID);
		try
		{
			LogHelper.info(OBJLoader.INSTANCE);
			OBJModel model;
			model = (OBJModel) OBJLoader.INSTANCE.loadModel(new ResourceLocation(ModInfo.ID, "models/block/enchanter/book.obj"));
			bookModel = (OBJModel.OBJBakedModel) model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, textureGetter::apply);
			model = (OBJModel) OBJLoader.INSTANCE.loadModel(new ResourceLocation(ModInfo.ID, "models/block/enchanter/books.obj"));
			ringModel = (OBJModel.OBJBakedModel) model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, textureGetter::apply);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntityEnchantingTable table, double x, double y, double z, float partialTicks, int destroyStage)
	{
		LogHelper.info("Rendering");
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5f, y, z + .5f);
		renderBook(table, partialTicks);
		if (table.getUpgrade() == EnumUpgrade.DIAMOND)
		{
			renderRing(partialTicks);
		}
		GlStateManager.popMatrix();
	}

	private void renderBook(TileEntityEnchantingTable table, float partialTicks)
	{
		GlStateManager.pushMatrix();
		float f = (float)table.tickCount + partialTicks;
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
		bindTexture(texture);

		GlStateManager.enableCull();
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightnessColor(bookModel, 0, 0, 0, 0);
		GlStateManager.popMatrix();
	}

	private void renderRing(float partialTicks)
	{

	}
}
