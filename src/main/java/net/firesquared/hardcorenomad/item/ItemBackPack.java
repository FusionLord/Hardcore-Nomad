package net.firesquared.hardcorenomad.item;

import net.minecraft.item.ItemArmor;

public abstract class ItemBackPack extends ItemArmor
{

	public ItemBackPack(int renderID) 
	{
		super(ArmorMaterial.CLOTH, renderID, 1);
	}

}
