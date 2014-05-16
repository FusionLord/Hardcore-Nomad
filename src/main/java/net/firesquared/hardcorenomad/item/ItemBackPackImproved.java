package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

public class ItemBackPackImproved extends ItemBackPack
{

	public ItemBackPackImproved(int renderID)
	{
		super(renderID);
	}

	public BackPackTypes getBackPackType() {
		return BackPackTypes.BACKPACK_IMPROVED;
	}
}
