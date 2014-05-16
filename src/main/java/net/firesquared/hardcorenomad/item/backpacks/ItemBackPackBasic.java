package net.firesquared.hardcorenomad.item.backpacks;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

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
