package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

public class ItemBackPackImproved extends ItemBackPack
{

	public ItemBackPackImproved(int renderID)
	{
		super(renderID);
	}

	public BackPackTypes getBackPackType()
	{
		return BackPackTypes.BACKPACK_IMPROVED;
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
