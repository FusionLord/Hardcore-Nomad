package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCobbleGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCobbleGenerator extends BlockCampComponent
{

	public BlockCobbleGenerator()
	{
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCobbleGenerator();
	}

	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_COBBLEGEN.block);

		TileEntityCobbleGenerator tileEntityCobbleGenerator = Tiles.<TileEntityCobbleGenerator>getTileEntity(world, x, y, z);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		tileEntityCobbleGenerator.writeToNBT(nbtTagCompound);

		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}

	@Override
	public UpgradeType getType()
	{
		return UpgradeType.COBBLE_GENERATOR;
	}

}
