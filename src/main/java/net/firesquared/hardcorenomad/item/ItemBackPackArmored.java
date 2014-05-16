package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

public class ItemBackPackArmored extends ItemBackPack
{

	public ItemBackPackArmored(int renderID)
	{
		super(renderID);
	}

	public BackPackTypes getBackPackType()
	{
		return BackPackTypes.BACKPACK_ARMORED;
	}

	@Override
	protected int getWeightCap()
	{
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	protected int invSize()
	{
		// TODO Auto-generated method stub
		return 36;
	}
}
