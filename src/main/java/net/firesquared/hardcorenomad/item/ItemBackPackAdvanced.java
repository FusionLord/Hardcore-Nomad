package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.BackPackTypes;

public class ItemBackPackAdvanced extends ItemBackPack {

	public ItemBackPackAdvanced(int renderID) 
	{
		super(renderID);
	}

	public BackPackTypes getBackPackType() {
		return BackPackTypes.BACKPACK_ADVANCED;
	}

}
