package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackType;

public class ItemBackPackBasic extends ItemBackPackOLD
{

	public ItemBackPackBasic()
	{
		super();
	}

	public BackPackType getBackPackType()
	{
		return BackPackType.BACKPACK_BASIC;
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
