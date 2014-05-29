package net.firesquared.hardcorenomad.block;

import java.util.ArrayList;
import java.util.List;

import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	public TileEntity createNewTileEntity(World world, int meta)
	{
		UpgradeType type = getType();
		if(type.tileEntityClass != null)
			try
			{
				return type.tileEntityClass.newInstance();
			}
			catch(Exception e){e.printStackTrace();}
		return new TileEntityDeployableBase(type);
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
			if(deployableBase.isDuplicate)
				return;
			dropBlockAsItem(world, x, y, z, itemstack);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int meta)
	{
		return !has3dRender() && super.isBlockSolid(world, x, y, z, meta);
	}
	
	protected abstract boolean has3dRender();
	
	@Override
	public boolean isOpaqueCube()
	{
		return !has3dRender() && super.isOpaqueCube();
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
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return !has3dRender() && super.isSideSolid(world, x, y, z, side);
	}

	@Override
	public boolean isNormalCube()
	{
		return !has3dRender() && super.isNormalCube();
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return !has3dRender() && super.isBlockNormalCube();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return !has3dRender() && super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return !has3dRender() && super.renderAsNormalBlock();
	}
	
	public abstract UpgradeType getType();
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX,
			float hitY, float hitZ, float unknown)
	{
		return super.onBlockActivated(world, x, y, z, player, hitX, hitY, hitZ, unknown);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB boundingBox, List collisionBoxes,
			Entity entity)
	{
		super.addCollisionBoxesToList(world, x, y, z, boundingBox, collisionBoxes, entity);
	}
	
	
	

}
