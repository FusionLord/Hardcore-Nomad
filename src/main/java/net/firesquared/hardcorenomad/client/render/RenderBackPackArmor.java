package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class RenderBackPackArmor extends ModelBiped
{
	IModelCustom model = null;
	ResourceLocation texture = null;

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		int backPackType = 0;

		ItemStack itemStack = null;

		if (par1Entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)par1Entity;
			itemStack = player.inventory.armorInventory[2];
		}

		if (itemStack == null)
			return;

		if (itemStack.getItem() == Items.ITEM_BACKPACKBASIC.getItem())
			backPackType = 0;
		if (itemStack.getItem() == Items.ITEM_BACKPACKIMPROVED.getItem())
			backPackType = 1;
		if (itemStack.getItem() == Items.ITEM_BACKPACKADVANCED.getItem())
			backPackType = 2;
		if (itemStack.getItem() == Items.ITEM_BACKPACKARMORED.getItem())
			backPackType = 3;

		texture = ModelRegistry.getTexture(Models.BACKPACK, backPackType);
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 2.40f, .34f);
		GL11.glScalef(.35f, .35f, .35f);
		GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
