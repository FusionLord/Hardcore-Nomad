package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.CobbleGeneratorTypes;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCobbleGenerator extends TileEntityDeployableBase
{
	public CobbleGeneratorTypes getCobbleGeneratorType() {
		return CobbleGeneratorTypes.TIER_1;
	}
}
