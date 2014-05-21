package net.firesquared.hardcorenomad.client.render.backpack;

import net.firesquared.hardcorenomad.helpers.ModelRegistry;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.helpers.enums.Models;
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
	public void render(Entity entity, float x, float y, float z, float yaw, float pitch, float roll)
	{
		ItemStack itemStack = null;

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			itemStack = player.inventory.armorInventory[2];
		}

		if (itemStack == null)
			return;

		model = ModelRegistry.getModel(Models.BACKPACK);
		texture = ModelRegistry.getTexture(Models.BACKPACK, itemStack.getItemDamage());
		ModelRegistry.bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 2.40f, .34f);
		GL11.glScalef(.35f, .35f, .35f);
		GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
