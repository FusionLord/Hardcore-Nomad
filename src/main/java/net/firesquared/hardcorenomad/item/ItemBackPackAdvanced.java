package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

public class ItemBackPackAdvanced extends ItemBackPack
{

	public ItemBackPackAdvanced(int renderID)
	{
		super(renderID);
	}

	public BackPackTypes getBackPackType()
	{
		return BackPackTypes.BACKPACK_ADVANCED;
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
