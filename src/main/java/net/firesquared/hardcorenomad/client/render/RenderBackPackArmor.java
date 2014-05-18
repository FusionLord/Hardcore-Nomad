package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderBackPackArmor extends ModelBiped
{
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/backpack/backpack.obj"));

	public static final ResourceLocation[] texture = {
			new ResourceLocation("hardcorenomad:models/backpack/backpackt1.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt2.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt3.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt4.png")
	};

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		int backPackType = 0;

		NBTTagCompound nbtTagCompound = new NBTTagCompound();

		if (par1Entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)par1Entity;
			nbtTagCompound = player.inventory.armorInventory[2].getTagCompound();
		}

		switch(BackPackTypes.values()[nbtTagCompound.getInteger("backPackType")])
		{
			case BACKPACK_BASIC:
				backPackType = 0;
				break;
			case BACKPACK_IMPROVED:
				backPackType = 1;
				break;
			case BACKPACK_ADVANCED:
				backPackType = 2;
				break;
			case BACKPACK_ARMORED:
				backPackType = 3;
				break;
		}

		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture[backPackType]);
		GL11.glTranslatef(0, 2.40f, .25f);
		GL11.glScalef(.35f, .35f, .35f);
		GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
