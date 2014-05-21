package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCampComponent extends BlockContainer
{
	protected BlockCampComponent(Material material)
	{
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityDeployableBase deployableBase;
		TileEntityBackPack backpack;
		deployableBase = (TileEntityDeployableBase)world.getTileEntity(x, y, z);
		backpack = (TileEntityBackPack)world.getTileEntity(x - deployableBase.getXOffset(), y - deployableBase.getYOffset(), z - deployableBase.getZOffset());

		ItemStack itemStack = backpack.getComponentForDropping(deployableBase.getComponentType());

		if (itemStack == null)
		{
			itemStack = new ItemStack(deployableBase.getBlockType(), 1);
			NBTTagCompound tag = new NBTTagCompound();
			deployableBase.writeToNBT(tag);
			itemStack.setTagCompound(tag);
		}

		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int meta)
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int meta)
	{
		return false;
	}

}
