package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.helpers.CobbleGeneratorTypes;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.tile.TileEntityCobbleGenerator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCobbleGenerator extends BlockContainer implements IBlockCampComponent
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

	public CobbleGeneratorTypes getCampFireType(World world, int x, int y, int z) {
		TileEntityCobbleGenerator tileEntityCampFire = TileEntityHelper.getTileEntity(world, x, y, z, TileEntityCobbleGenerator.class);
		return tileEntityCampFire.getCobbleGeneratorType();
	}

	@Override
	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_COBBLEGEN.getBlock());

		TileEntityCobbleGenerator tileEntityCobbleGenerator = TileEntityHelper.getTileEntity(world, x, y, z, TileEntityCobbleGenerator.class);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		tileEntityCobbleGenerator.writeToNBT(nbtTagCompound);

		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}

}
