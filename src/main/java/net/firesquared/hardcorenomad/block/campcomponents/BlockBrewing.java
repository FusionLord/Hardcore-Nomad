package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.minecraft.block.material.Material;

public class BlockBrewing extends BlockCampComponent
{

	public BlockBrewing()
	{
		super(Material.rock);
	}

	@Override
	protected boolean has3dRender()
	{
		return false;
	}
	
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.BREWING_STAND;
	}
}
