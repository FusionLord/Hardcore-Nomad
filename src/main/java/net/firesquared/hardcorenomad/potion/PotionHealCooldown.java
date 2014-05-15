package net.firesquared.hardcorenomad.potion;

import net.minecraft.potion.Potion;

public class PotionHealCooldown extends Potion
{
	protected PotionHealCooldown(int par1, boolean par2, int par3)
	{
		super(par1, par2, par3);
	}

	public int getId()
	{
		return this.id;
	}
}
