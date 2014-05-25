package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
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

public abstract class BlockCampComponent extends BlockContainer
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
		
		if(deployableBase.hasParrent())
		{
			backpack = deployableBase.getParrent();
	
			ItemStack itemStack = backpack.getComponentForDropping(deployableBase.getComponentType());
	
			if (itemStack == null)
			{
				itemStack = new ItemStack(deployableBase.getBlockType(), 1);
				NBTTagCompound tag = new NBTTagCompound();
				deployableBase.writeToNBT(tag);
				itemStack.setTagCompound(tag);
			}
			
			itemStack.setItemDamage(meta);
	
			dropBlockAsItem(world, x, y, z, itemStack);
		}
		else
		{
			ItemStack itemstack = new ItemStack(this, 1, deployableBase.getBlockMetadata());
			itemstack.stackTagCompound = new NBTTagCompound();
			deployableBase.writeToNBT(itemstack.stackTagCompound);
			dropBlockAsItem(world, x, y, z, itemstack);
		}
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int meta)
	{
		return has3dRender() ? false : super.isBlockSolid(world, x, y, z, meta);
	}
	
	protected abstract boolean has3dRender();
	
	@Override
	public boolean isOpaqueCube()
	{
		return has3dRender() ? false : super.isOpaqueCube();
	}
	
	@Override
	public boolean getCanBlockGrass()
	{
		return false;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return has3dRender() ? -1 : 0;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return has3dRender() ? false : super.renderAsNormalBlock();
	}
	
	public abstract UpgradeType getType();

}
