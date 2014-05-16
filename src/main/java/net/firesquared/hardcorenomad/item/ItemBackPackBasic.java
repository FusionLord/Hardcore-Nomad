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

	@Override
	protected int invSize()
	{
		// TODO Auto-generated method stub
		return 6;
	}
}
