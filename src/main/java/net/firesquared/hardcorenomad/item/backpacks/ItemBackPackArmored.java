package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.minecraft.item.ItemStack;

public class ItemBackPackArmored extends ItemBackPackOLD
{

	public ItemBackPackArmored()
	{
		super();
	}

	public BackPackType getBackPackType()
	{
		return BackPackType.BACKPACK_ARMORED;
	}

	@Override
	protected int getWeightCap()
	{
		return 20;
	}

	@Override
	protected int invSize()
	{
		return 36;
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		//NOTE: DO NOT CALL SUPER!!!
		//TODO: damage item inside for gui indication of the damage on the backpack's armor
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return super.getMaxDamage(stack); // TODO: add fetch for armor in the bag
	}
}
