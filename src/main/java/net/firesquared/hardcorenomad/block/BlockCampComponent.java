package net.firesquared.hardcorenomad.block;

import java.util.ArrayList;
import java.util.List;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.Vector3n;
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
	private UpgradeType upgradeType = null;
	protected BlockCampComponent(Material material)
	{
		super(material);
	}
	
	@SuppressWarnings("unused")//actually used by some reflective code
	private final void setUpgradeType(UpgradeType type)
	{
		upgradeType = type;
	}
	
	public final UpgradeType getUpgradeType()
	{
		return upgradeType;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		if(upgradeType.tileEntityClass != null)
			try
			{
				return upgradeType.tileEntityClass.newInstance();
			}
			catch(Exception e){e.printStackTrace();}
		return new TileEntityDeployableBase(upgradeType);
	}

	@Override
	public final void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityDeployableBase deployableBase = Tiles.<TileEntityDeployableBase>getTileEntity(world, x, y, z);
		TileEntityBackPack backpack;
		
		if(deployableBase.isDuplicate)
			return;
		
		backpack = deployableBase.getParrent();

		if(backpack != null)
			backpack.notifyBreak(new Vector3n(x, y, z));
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		getDrops(deployableBase, meta, list);
		for(ItemStack is : list)
		{
			dropBlockAsItem(world, x, y, z, is);
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	protected List<ItemStack> getDrops(TileEntityDeployableBase deployableBase, 
			@SuppressWarnings("unused") int meta, List<ItemStack> list)
	{
		ItemStack is = new ItemStack(deployableBase.getComponentType().blockContainer);
		is.stackTagCompound = new  NBTTagCompound();
		deployableBase.writeExtraNBT(is.stackTagCompound);
		list.add(is);
		return list;
	}
	
	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
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
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX,
			float hitY, float hitZ, float unknown)
	{
		if(getUpgradeType().guiType != null)
		{
			if(!world.isRemote)
				player.openGui(HardcoreNomad.instance, getUpgradeType().guiType.ID, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB boundingBox, List collisionBoxes,
			Entity entity)
	{
		super.addCollisionBoxesToList(world, x, y, z, boundingBox, collisionBoxes, entity);
	}
	
	
	

}
