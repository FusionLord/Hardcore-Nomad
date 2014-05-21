package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackType;

public class ItemBackPackImproved extends ItemBackPackOLD
{

	public ItemBackPackImproved()
	{
		super();
	}

	public BackPackType getBackPackType()
	{
		return BackPackType.BACKPACK_IMPROVED;
	}

	@Override
	protected int getWeightCap()
	{
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected int invSize()
	{
		// TODO Auto-generated method stub
		return 12;
	}
}
