package net.firesquared.hardcorenomad.tile.campcomponents;

import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;

public class TileEntityCobbleGenerator extends TileEntityDeployableBase
{
	private int currentProcessTime;

	public TileEntityCobbleGenerator()
	{
		super(UpgradeType.COBBLE_GENERATOR);
	}

	public int getCurrentProcessTime()
	{
		return currentProcessTime;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (currentProcessTime++ > 1000)
		{
			currentProcessTime = 0;
		}
	}
}
