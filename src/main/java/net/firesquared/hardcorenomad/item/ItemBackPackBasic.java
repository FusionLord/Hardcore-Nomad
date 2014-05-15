package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.client.render.RenderBackPackArmor;
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
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return new RenderBackPackArmor();
	}

}
