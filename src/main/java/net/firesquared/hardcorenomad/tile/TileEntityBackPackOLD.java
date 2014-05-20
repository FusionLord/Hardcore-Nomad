

package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.firesquared.hardcorenomad.block.EnumSlotCoordinateOffsets;
import net.firesquared.hardcorenomad.block.IBlockCampComponent;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.item.backpacks.BackPackInventoryOLD;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBackPackOLD extends TileEntityDeployableBase implements IInventory
{
	protected int blockMeta;
	protected int backPackType;
	protected BackPackInventoryOLD inventory;
	private NBTTagCompound tagInv;
	public ItemStack upgradeSlot;
	BackPackType type = BackPackType.BACKPACK_BASIC;
	
	public void setTagInv(NBTTagCompound tag)
	{
		tagInv = tag;
		inventory = new BackPackInventoryOLD(tagInv);
	}
	
	public void setBackpackType(BackPackType type)
	{
		this.type = type;
	}
	
	public BackPackType getBackpackType()
	{
		return type;
	}

	public static final int ModelID = RenderingRegistry.getNextAvailableRenderId();

	public TileEntityBackPackOLD()
	{
		super(ComponentType.BACKPACK);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		type = BackPackType.values()[tag.getInteger("backPackType")];
		if(tag.hasKey("tagInv"))
			setTagInv(tag.getCompoundTag("tagInv"));
		else
			LogHelper.debug("WHERE'S MY NBT DATA!?");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		if(tagInv != null)
			tag.setTag("tagInv", tagInv);
		else
			LogHelper.debug("Backpack NBT tag is misssssssiiiiiiinnnnnnggggggg!!!!!!!!");
		tag.setInteger("backPackType", type.ordinal());
	}

	/**
	 * This will set the block meta data to the tile entity
	 *
	 * @param meta
	 */
	public void setBlockMeta(int meta)
	{
		blockMeta = meta;
	}

	/**
	 * Get the block meta data
	 *
	 * @return
	 */
	public int getBlockMeta()
	{
		return blockMeta;
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return inventory.getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		return inventory.decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return inventory.getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		inventory.setInventorySlotContents(var1, var2);
	}

	@Override
	public String getInventoryName()
	{
		return inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return inventory.isUseableByPlayer(var1);
	}

	@Override
	public void openInventory()
	{
		inventory.openInventory();
	}

	@Override
	public void closeInventory()
	{
		inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		return inventory.isItemValidForSlot(var1, var2);
	}
	
	private NBTTagCompound getUpgrade(int i)
	{
		if(!tagInv.hasKey("ups"+i))
			return null;
		return tagInv.getCompoundTag("ups"+i);
	}
	
	private NBTTagCompound setUpgrade(int i, NBTTagCompound tag)
	{
		NBTTagCompound temp = tagInv.getCompoundTag("ups"+i);
		tagInv.setTag("ups"+i, tag);
		return temp;
	}
	
	public void toggle(int iD)
	{
		NBTTagCompound tag = getUpgrade(iD);
		if(tag != null)
		{
			if(!tag.getBoolean("deployed"))
				recover(iD);
			else
				deploy(iD);
		}
		
	}

	public void deploy(int iD)
	{
		if(iD == -1)
			for(int i = 0; i < 9; i++)
				deploy(i);
		else
		{
			NBTTagCompound tag = getUpgrade(iD);
			if(tag != null && !tag.getBoolean("deployed"))
			{
				try
				{
				ItemStack is = ItemStack.loadItemStackFromNBT(tag);
				EnumSlotCoordinateOffsets offs = EnumSlotCoordinateOffsets.values()[iD];
				
				is.stackTagCompound.setInteger("x", xCoord + offs.x);
				is.stackTagCompound.setInteger("y", yCoord + offs.y);
				is.stackTagCompound.setInteger("z", zCoord + offs.z);
				
				worldObj.setBlock(xCoord + offs.x, yCoord + offs.y, zCoord + offs.z, (BlockContainer)Block.getBlockFromItem(is.getItem()));
				worldObj.getTileEntity(xCoord + offs.x, yCoord + offs.y, zCoord + offs.z).readFromNBT(is.stackTagCompound);
			
				}
				catch(Exception e)
				{
					LogHelper.error("NBT issue on toggle");
				}
			}
		}
	}
	
	public void recover(int iD)
	{
		if(iD == -1)
			for(int i = 0; i < 9; i++)
				recover(i);
		else
		{
			NBTTagCompound tag = getUpgrade(iD);
			EnumSlotCoordinateOffsets offs = EnumSlotCoordinateOffsets.values()[iD];
			Block b = worldObj.getBlock(xCoord + offs.x, yCoord + offs.y, zCoord + offs.z);
			if(b instanceof IBlockCampComponent)
			{
				ItemStack is = ((IBlockCampComponent)b).packIntoItemStack(worldObj, xCoord + offs.x, yCoord + offs.y, zCoord + offs.z);
				is.writeToNBT(tag);
				worldObj.setBlockToAir(xCoord + offs.x, yCoord + offs.y, zCoord + offs.z);
				worldObj.removeTileEntity(xCoord + offs.x, yCoord + offs.y, zCoord + offs.z);
			}
		}
	}
	
	public boolean doUpgrade()
	{
//		upgradeSlot = getStackInSlot(-1);
//		if(upgradeSlot == null || !(upgradeSlot.getItem() instanceof ItemUpgrade))
//			return false;
//		NBTTagCompound tag;
//		ItemStack is;
//		ItemUpgrade iu = (ItemUpgrade)upgradeSlot.getItem();
//		for(int i = 0; i < 9; i++)
//		{
//			tag = getUpgrade(i);
//			if(tag != null)
//			{
//				is = ItemStack.loadItemStackFromNBT(tag);
//				if(iu == is.getItem())
//				{
//					applyUpgrade(is, iu);
//					is.writeToNBT(tag);
//					setInventorySlotContents(-1, null);
//					return true;
//				}
//			}
//		}
//
//		if(ItemUpgrade.getUpgradeLevel(upgradeSlot.getItemDamage()) == 0)
//			for(int i = 0; i < 9; i++)
//			{
//				tag = getUpgrade(i);
//				if(tag == null)
//				{
//					is = new ItemStack(iu.getContainerSingleton());
//					is = applyUpgrade(is, iu);
//					tag = new NBTTagCompound();
//					is.writeToNBT(tag);
//					setUpgrade(i, tag);
//					setInventorySlotContents(-1, null);
//					return true;
//				}
//			}
//
		return false;		
	}
	
	private ItemStack applyUpgrade(ItemStack is, ItemUpgrade iu)
	{
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("upgradeLevel", ItemUpgrade.getUpgradeLevel(is.getItemDamage()));
		return is;
	}

}