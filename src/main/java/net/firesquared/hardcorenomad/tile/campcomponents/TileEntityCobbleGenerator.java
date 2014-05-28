package net.firesquared.hardcorenomad.tile.campcomponents;

import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;

public class TileEntityCobbleGenerator extends TileEntityDeployableBase
{
	private int currentProcessTime;
	private int currentMaxTime = 1000;
	private float percentage;
	private float rotation;

	public TileEntityCobbleGenerator()
	{
		super(UpgradeType.COBBLE_GENERATOR);
	}

	public int getCurrentProcessTime()
	{
		return currentProcessTime;
	}

	public int getCurrentMaxTime()
	{
		return currentMaxTime;
	}

	public float getPercentage()
	{
		return percentage;
	}

	public float getRotation()
	{
		return rotation;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (currentProcessTime++ > currentMaxTime)
		{
			currentProcessTime = 1;
		}
		percentage = (float)currentProcessTime / (float) currentMaxTime;
		rotation = percentage > .75f ? (1f - percentage) : 0f;
	}
}
