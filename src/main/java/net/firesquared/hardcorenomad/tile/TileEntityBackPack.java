

package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquaredcore.helper.Vector3n;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBackPack extends TileEntityDeployableBase implements IInventory
{
	//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
	//get the value from where it is defined.  Don't just put the current value in as an integer
	protected BackpackInvWrapper inv;

	//CONSTRUCTORS
	public TileEntityBackPack()
	{
		super(null);
	}

	public TileEntityBackPack(int metadata)
	{
		super(null);
		inv = new BackpackInvWrapper(BackPackType.fromLevel(metadata));
	}

	//NBT AND PERSISTENCE
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeExtraNBT(tag);
	}
	
	public void writeExtraNBT(NBTTagCompound tag)
	{
		BackpackInvWrapper.writeExtraNBT(tag, inv);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readExtraNBT(tag);
	}
	
	public void readExtraNBT(NBTTagCompound tag)
	{
		BackpackInvWrapper.readExtraNBT(tag, inv);
	}
	
	//HELPERS
	public BackPackType getType()
	{
		return BackPackType.fromLevel(getCurrentLevel());
	}
	
	public void setBlockMeta(int meta)
	{
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
	}
	
	public ItemStack getComponentForDropping(UpgradeType componentType)
	{
		if (componentType == null)
		{
			Helper.getLogger().warn("Unexpected null value in TileEntityBackpack");
			return null; //this shouldn't happen;
		}
		ItemStack itemStack = inv.componentInventory[componentType.ordinal()];
		inv.componentInventory[componentType.ordinal()] = null;
		return itemStack;
	}
	
	//UPGRADE AND DEPLOYMENT HANDLING
	/**
	 * Attempt to apply the upgrade currently in the upgrade slot
	 * @return true if the upgrade was successfully applied
	 */
	public boolean doUpgrade()
	{
		if (inv.upgradeSlot == null )
			return false;

		if(inv.upgradeSlot.getItem() instanceof ItemUpgrade)
		{
			ItemUpgrade upgrade = (ItemUpgrade)inv.upgradeSlot.getItem();
			int dmg = inv.upgradeSlot.getItemDamage(), 
					lvl = ItemUpgrade.getLevelFromDamage(dmg);
			UpgradeType type = ItemUpgrade.getTypeFromDamage(dmg);
			//if the user is upgrading their backpack in-place
			if(type == UpgradeType.BACKPACK)
			{
				int meta = getBlockMetadata();
				if(meta == lvl)
				{
					setBlockMeta(meta + 1);
					setCurrentLevel(meta + 1);
					Helper.getLogger().info("Applied upgrade "+inv.upgradeSlot.getDisplayName());
					inv.upgradeSlot = null;
					ItemStack[] isa = inv.storageInventory;
					inv.storageInventory = new ItemStack[getType().getStorageCount()];
					for(int i = 0; i < isa.length; i++)
						inv.storageInventory[i] = isa[i];
					inv.type = getType();
					markDirty();
					return true;
				}
				return false;
			}
			int typeIndex = type.ordinal(),
				currentLvl = inv.componentInventory[typeIndex] == null ? -1 : inv.componentInventory[typeIndex].getItemDamage();
			//if the user is upgrading one of their components and the upgrade levels match
			if(currentLvl + 1 == lvl)
			{
				if(inv.componentInventory[typeIndex] == null)
					inv.componentInventory[typeIndex] = new ItemStack(type.getBlockContainer());
				inv.componentInventory[typeIndex].setItemDamage(lvl);
				if(inv.componentInventory[typeIndex].stackTagCompound == null)
					inv.componentInventory[typeIndex].stackTagCompound = new NBTTagCompound();
				inv.upgradeSlot = null;
				return true;
			}
			return false;
		}
		else if(Block.getBlockFromItem(inv.upgradeSlot.getItem()) instanceof BlockCampComponent)
		{
			BlockCampComponent block = (BlockCampComponent) Block.getBlockFromItem(inv.upgradeSlot.getItem());
			int index = block.getType().ordinal();
			if(inv.upgradeSlot == null)
			{
				inv.componentInventory[index] = inv.upgradeSlot;
				Helper.getLogger().info("Applied existing component to empty slot");
				inv.upgradeSlot = null;
				return true;
			}
			else
			{
				ItemStack temp = inv.componentInventory[index];
				inv.componentInventory[index] = inv.upgradeSlot;
				inv.upgradeSlot = temp;
				Helper.getLogger().info("Swapped upgrade component with existing item in slot");
				return true;
			}
			
		}
		Helper.getLogger().warn("Had an invalid upgrade in the upgrade slot of a backpack which should not be there.");
		return false;
	}

	public void deployAll(EntityPlayer player)
	{
		NBTTagCompound tag;
		for (int i = 0; i < inv.componentInventory.length; i++)
			if(inv.componentInventory[i] != null)
			{
				tag = inv.componentInventory[i].stackTagCompound;
				if (tag == null) return;
				if(!tag.hasKey(NBTHelper.IS_DEPLOYED) || !tag.getBoolean(NBTHelper.IS_DEPLOYED))
				{
					toggle(i, player);
				}
			}
	}

	public void recoverAll(EntityPlayer player)
	{
		NBTTagCompound tag;
		for (int i = 0; i < inv.componentInventory.length; i++)
			if(inv.componentInventory[i] != null)
			{
				tag = inv.componentInventory[i].stackTagCompound;
				if (tag == null) return;
				if(tag.hasKey(NBTHelper.IS_DEPLOYED) && tag.getBoolean(NBTHelper.IS_DEPLOYED))
				{
					toggle(i, player);
				}
			}
	}

	public boolean toggle(int componentID, EntityPlayer player)
	{
		ItemStack is = inv.componentInventory[componentID];
		if (is == null || is.stackTagCompound == null)
		{
			Helper.getLogger().error("Attempted to toggle component that " + (is == null ? "was null" : "had a missing tag"));
			return false;
		}
		
		Vector3n offset = readOffset(componentID);
		//if the thing doesn't have offset's, let's pull them from our nether regions
		while(offset == null || (offset.x == 0 && offset.z == 0))
			offset = new Vector3n(worldObj.rand.nextInt(19) - 9, 0, worldObj.rand.nextInt(19) - 9);
		writeOffset(componentID, offset);
			
		Vector3n absolute = new Vector3n(offset.x + xCoord, offset.y + yCoord, offset.z + zCoord);
		if(is.stackTagCompound.hasKey(NBTHelper.IS_DEPLOYED) && is.stackTagCompound.getBoolean(NBTHelper.IS_DEPLOYED))
		{
			BlockCampComponent b = (BlockCampComponent) Block.getBlockFromItem(is.getItem());
			return this.<TileEntityDeployableBase>doBlockRecovery(absolute, componentID, b);
		}
		else
		{
			if(!isPlacementValid(absolute, player, is))
				return false;
			return doBlockSetting(absolute, is, is.getItemDamage());
		}
	}
	
	private boolean isPlacementValid(Vector3n location, EntityPlayer player, ItemStack is)
	{
		int x = location.x, y = location.y, z = location.z;
		Block b = worldObj.getBlock(x,  y,  z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		if(b.isReplaceable(worldObj, x, y, z) && player.canPlayerEdit(x, y, z, meta, is))
			return true;
		return false;
	}
	
	private <TE extends TileEntityDeployableBase> 
		boolean doBlockRecovery(Vector3n coords, int slotIndex, BlockCampComponent blockInst)
	{
		int x = coords.x, y = coords.y, z = coords.z;
		ItemStack result = inv.componentInventory[slotIndex];
		if(result == null || result.stackTagCompound == null)
		{
			Helper.getLogger().error("Attempted to recover a camp component with no, or corrupt, location data");
			return false;
		}
		Block b = worldObj.getBlock(x, y, z);
		
		if(!blockInst.getClass().isInstance(b))
		{
			Helper.getLogger().error("Found unexpected block at target location");
			return false;
		}
			
		TE tile = Tiles.<TE>getTileEntity(worldObj, x, y, z);
		if(tile == null)
		{
			Helper.getLogger().error("Specified tile entity was not found at target location");
			return false;
		}
		tile.writeToNBT(result.stackTagCompound);
		tile.isDuplicate = true;
		worldObj.setBlockToAir(x, y, z);
		result.stackTagCompound.setBoolean(NBTHelper.IS_DEPLOYED, false);
		return true;
		
	}
	
	private <TE extends TileEntityDeployableBase>boolean doBlockSetting(Vector3n coords, ItemStack is, int level)
	{
		int x = coords.x, y = coords.y, z = coords.z;
		TE teComponent;
		if (worldObj.setBlock(x, y, z, Block.getBlockFromItem(is.getItem())))
		{
			if(!(worldObj.getBlock(x, y, z) instanceof BlockCampComponent));
			{
				Helper.getLogger().warn("Failed to confirm block placement at ".concat(coords.toString()));
			}
			worldObj.setBlockMetadataWithNotify(x, y, z, level, 3);
			teComponent = Tiles.<TE>getTileEntity(worldObj, x, y, z);
			if(teComponent != null)
			{
				if(is.stackTagCompound != null)
				{
					teComponent.readFromNBT(is.stackTagCompound);
					is.stackTagCompound.setBoolean(NBTHelper.IS_DEPLOYED, true);
					return true;
				}
				else
				{
					Helper.getLogger().warn("ItemStack is doesn't have an nbt tag");
					return false;
				}
			}
			else
			{
				Helper.getLogger().warn("Failed to validate tile entity at ".concat(coords.toString()));
				return false;
			}
				
		}
		else
			return false;
	}
	
	private Vector3n readOffset(int slot)
	{
		ItemStack is = inv.componentInventory[slot];
		NBTTagCompound comTag = is.getTagCompound();
		Vector3n vec = NBTHelper.getXYZ(comTag, NBTHelper.OFFSET);
		if(vec == null)
		{
			promptUserForOffsets(slot, is);
			return null;
		}
		return vec;
	}
	
	private void writeOffset(int slot, Vector3n newOffset)
	{
		ItemStack is = inv.componentInventory[slot];
		NBTTagCompound comTag = is.getTagCompound();
		NBTHelper.setXYZ(comTag, NBTHelper.OFFSET, newOffset);
	}

	private void promptUserForOffsets(int slot, ItemStack is)
	{
		
	}

	//Inventory; redirects all calls to the wrapper
	@Override
	public int getSizeInventory()
	{
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inv.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int value)
	{
		return inv.decrStackSize(slot, value);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return inv.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		inv.setInventorySlotContents(slot, itemStack);
	}

	@Override
	public String getInventoryName()
	{
		return inv.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return inv.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inv.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inv.isUseableByPlayer(player);
	}

	@Override
	public void openInventory()
	{
		inv.openInventory();
	}

	@Override
	public void closeInventory()
	{
		inv.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return inv.isItemValidForSlot(slot, itemStack);
	}

}
