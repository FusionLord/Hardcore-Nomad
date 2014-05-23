package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCobbleGenerator;
import net.minecraft.block.BlockContainer;
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
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		// TODO: Make a tile entity for the crafting table :/
		return null;
	}

	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_COBBLEGEN.getBlock());

		TileEntityCobbleGenerator tileEntityCobbleGenerator = Tiles.<TileEntityCobbleGenerator>getTileEntity(world, x, y, z);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		tileEntityCobbleGenerator.writeToNBT(nbtTagCompound);

		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}

	@Override
	protected boolean has3dRender()
	{
		return false;
	}
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.CobbleGen;
	}

}
