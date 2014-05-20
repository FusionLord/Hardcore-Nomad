package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackType;

public class ItemBackPackAdvanced extends ItemBackPackOLD
{

	public ItemBackPackAdvanced()
	{
		super();
	}

	public BackPackType getBackPackType()
	{
		return BackPackType.BACKPACK_ADVANCED;
	}

	@Override
	protected int getWeightCap()
	{
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	protected int invSize()
	{
		// TODO Auto-generated method stub
		return 21;
	}

}
