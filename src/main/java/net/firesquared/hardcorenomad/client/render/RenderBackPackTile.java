package net.firesquared.hardcorenomad.client.render;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderBackPackTile extends TileEntitySpecialRenderer
{
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("hardcorenomad:models/backpack/backpack.obj"));

	public static final ResourceLocation[] texture = {
			new ResourceLocation("hardcorenomad:models/backpack/backpackt1.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt2.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt3.png"),
			new ResourceLocation("hardcorenomad:models/backpack/backpackt4.png")
	};

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8)
	{
		int backPackType = 0;

		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		te.writeToNBT(nbtTagCompound);

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
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, -1.44f, 0.5f);
		GL11.glScalef(.35f, .35f, .35f);
		model.renderAll();
		GL11.glPopMatrix();
	}

}
