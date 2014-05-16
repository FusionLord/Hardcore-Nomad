package net.firesquared.hardcorenomad.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.client.render.RenderBackPackArmor;
import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemBackPackBasic extends ItemBackPack
{

	public ItemBackPackBasic(int renderID)
	{
		super(renderID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return new RenderBackPackArmor();
	}

	public BackPackTypes getBackPackType()
	{
		return BackPackTypes.BACKPACK_BASIC;
	}

	@Override
	protected int getWeightCap()
	{
		// TODO Auto-generated method stub
		return 5;
	}
}
