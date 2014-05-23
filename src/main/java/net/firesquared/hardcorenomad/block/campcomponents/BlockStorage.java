package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStorage extends BlockCampComponent
{

	public BlockStorage()
	{
		super(Material.wood);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		// TODO: Make a tile entity for the crafting table :/
		return null;
	}

	@Override
	protected boolean has3dRender()
	{
		return false;
	}
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.Storage;
	}
}
