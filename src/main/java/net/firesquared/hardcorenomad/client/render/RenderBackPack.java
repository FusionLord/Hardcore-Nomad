

package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderBackPack implements IItemRenderer
{
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/backpack/backpack.obj"));

	public static final ResourceLocation[] texture = {
			new ResourceLocation("hardcorenomad:models/backpack/backpackt1.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt2.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt3.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt4.png")
	};

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		int backPackType = 0;

		if (item.getItem() == Items.ITEM_BACKPACKBASIC.getItem())
			backPackType = 0;
		if (item.getItem() == Items.ITEM_BACKPACKIMPROVED.getItem())
			backPackType = 1;
		if (item.getItem() == Items.ITEM_BACKPACKADVANCED.getItem())
			backPackType = 2;
		if (item.getItem() == Items.ITEM_BACKPACKARMORED.getItem())
			backPackType = 3;

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture[backPackType]);
		GL11.glScalef(.44f, .44f, .44f);
		GL11.glTranslatef(0, -5.5f, 0);
		GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1, 1, 1, 1);
		//Tessellator.instance.setColorOpaque_F(1, 1, 1);
		//Tessellator.instance.setBrightness(255);

		model.renderAll();
		GL11.glPopMatrix();
	}
}
