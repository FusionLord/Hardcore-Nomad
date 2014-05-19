package net.firesquared.hardcorenomad.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.firesquared.hardcorenomad.block.BlockEnchantmentTable;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.upgrades.itemUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderUpgradeItem implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,	ItemRendererHelper helper)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
	{
		IModelCustom model = null;
		ResourceLocation texture = null;
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;

		float scale = 0, xOffset = 0, yOffest = 0, zOffset = 0, rotX = 0, rotY = 0, rotZ = 0;

		itemUpgrade item = (itemUpgrade)itemStack.getItem();

		GL11.glPushMatrix();

		if (item.getUpgradeTarget() == BlockEnchantmentTable.class)
		{
			//LogHelper.debug("calling");
			model = ModelRegistry.getModel(Models.MODEL_ENCHANTING_TABLE, item.getTargetLevel());
			texture = ModelRegistry.getTexture(Models.MODEL_ENCHANTING_TABLE);

			scale = .25f;
			yOffest = .5f;
		}
		if(model == null)
		{
			LogHelper.fatal("How did you even? Cannot render model.");
			return;
		}

		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(xOffset, yOffest, zOffset);
		//GL11.glRotatef(90, rotX, rotY, rotZ);

		FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);

		model.renderAll();

		//GL11.glRotatef(90, -rotX, -rotY, -rotZ);
		GL11.glTranslatef(-xOffset, -yOffest, -zOffset);
		GL11.glScalef(-scale, -scale, -scale);

		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glRotatef(225, 0f, 1f, 0f);
			GL11.glTranslatef(0f, 0f, 20f);
			RenderHelper.disableStandardItemLighting();
			font.drawString(Integer.toString(item.getTargetLevel() + 1), -12, -25, 0xFFFF00);
			RenderHelper.enableGUIStandardItemLighting();
		}

		GL11.glPopMatrix();

	}


}
