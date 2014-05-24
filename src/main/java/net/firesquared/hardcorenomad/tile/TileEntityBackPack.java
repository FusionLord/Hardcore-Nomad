

package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityBackPack extends TileEntityDeployableBase implements IInventory
{
	//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
	//get the value from where it is defined.  Don't just put the current value in as an integer
	protected ItemStack[] storageInventory;
	//slot for everything excluding the backpack upgrade
	protected ItemStack[] componentInventory = new ItemStack[ItemUpgrade.getCampComponentCount()];
	protected ItemStack upgradeSlot;
	protected ItemStack armorSlot;


	public TileEntityBackPack()
	{
		super(null);
	}

	public TileEntityBackPack(int metadata)
	{
		super(null, metadata);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeExtraNBT(tag);
	}

	public void writeExtraNBT(NBTTagCompound tag)
	{
		NBTTagCompound comInvTag = new NBTTagCompound();
		for (int i = 0; i < componentInventory.length; i++)
		{
			if(componentInventory[i]!=null)
				comInvTag.setTag(NBTHelper.SLOT.concat(""+i), componentInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.COMINV, comInvTag);
		
		NBTTagCompound stgInvTag = new NBTTagCompound();
		int stgInvSize = storageInventory.length;
		for (int i = 0; i < stgInvSize; i++)
		{
			if(storageInventory[i]!=null)
				stgInvTag.setTag(NBTHelper.SLOT.concat(""+i), storageInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.STGINV, stgInvTag);
		
		if(upgradeSlot != null)
			tag.setTag(NBTHelper.UPGRADESLOT, upgradeSlot.writeToNBT(new NBTTagCompound()));
		
		if(getType().hasArmorSlot() && armorSlot != null)
			tag.setTag(NBTHelper.ARMORSLOT, armorSlot.writeToNBT(new NBTTagCompound()));
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readExtraNBT(tag);
	}

	public void readExtraNBT(NBTTagCompound tag)
	{
		NBTTagCompound comInvTag = tag.getCompoundTag(NBTHelper.COMINV);
		for (int i = 0; i < componentInventory.length; i++)
		{
			if(comInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				componentInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		storageInventory = new ItemStack[getType().getStorageCount()];
		for (int i = 0; i < storageInventory.length; i++)
		{
			if(stgInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				storageInventory[i] = ItemStack.loadItemStackFromNBT(stgInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		if(tag.hasKey(NBTHelper.UPGRADESLOT))
			upgradeSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.UPGRADESLOT));
		if(getType().hasArmorSlot() && tag.hasKey(NBTHelper.ARMORSLOT))
			armorSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
		markDirty();
	}

	@Override
	public int getSizeInventory()
	{
		return getType().getStorageCount();
	}
	
	public BackPackType getType()
	{
		return BackPackType.values()[Math.max(0, 
				Math.min(getCurrentLevel(), 
						BackPackType.values().length-1))];
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		//Helper.getLogger().info((worldObj.isRemote?"Client":"Server")+" getting slot contents at index " + slot);
		//TODO: change so that the last 3 levels can all mount some form of armor
		//improved limited to un-enchanted iron or leather armor only; possibly also wood armor from TC
		//advanced can have any kind of un-enchanted, vanilla armor
		//aromored has no restrictions, beyond that the item extend ItemArmor
		boolean isArmor = getType().hasArmorSlot();

		if (slot < storageInventory.length)
			return storageInventory[slot];
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			return componentInventory[slot - storageInventory.length];
		if (slot == storageInventory.length + componentInventory.length)
			return upgradeSlot;
		if (slot == storageInventory.length + componentInventory.length + 1 && isArmor)
			return armorSlot;
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int value)
	{
		ItemStack isTemp = getStackInSlot(slot);
		if(isTemp == null)
			return null;
		if(isTemp.stackSize > value)
		{
			isTemp.stackSize -= value;
			setInventorySlotContents(slot, isTemp.copy());
			isTemp.stackSize = value;
			return isTemp;
		}
		else
		{
			setInventorySlotContents(slot, null);
			return isTemp;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		Helper.getLogger().info((worldObj.isRemote?"Client":"Server")+" setting slot contents at index " + slot);
		boolean isArmor = getType().hasArmorSlot();
		if(itemStack != null)
			itemStack = itemStack.copy();
		
		if (slot < storageInventory.length)
			storageInventory[slot] = itemStack;
		else if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			componentInventory[slot - storageInventory.length] = itemStack;
		else if (slot == storageInventory.length + componentInventory.length)
			upgradeSlot = itemStack;
		else if (slot == storageInventory.length + componentInventory.length + 1 && isArmor)
			armorSlot = itemStack;
	}

	@Override
	public String getInventoryName()
	{
		return StatCollector.translateToLocal("inventory.backpack.name");
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false; // TODO: add ability to change the backpack's name in the anvil?
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (slot < storageInventory.length)
			return true;
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			return Block.getBlockFromItem(item) instanceof BlockCampComponent;
		if (slot == storageInventory.length + componentInventory.length)
			return item instanceof ItemUpgrade || Block.getBlockFromItem(item) instanceof BlockCampComponent;
		if (slot == storageInventory.length + componentInventory.length + 1 && getType().hasArmorSlot())
			return item instanceof ItemArmor && !(item instanceof ItemBackPack);
		return false;
	}

	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}

	/**
	 * Attempt to apply the upgrade currently in the upgrade slot
	 * @return true if the upgrade was successfully applied
	 */
	public boolean doUpgrade()
	{
		if (upgradeSlot == null )
			return false;

		if(upgradeSlot.getItem() instanceof ItemUpgrade)
		{
			ItemUpgrade upgrade = (ItemUpgrade)upgradeSlot.getItem();
			int dmg = upgradeSlot.getItemDamage(), 
					lvl = ItemUpgrade.getLevelFromDamage(dmg);
			UpgradeType type = ItemUpgrade.getTypeFromDamage(dmg);
			//if the user is upgrading their backpack in-place
			if(type == UpgradeType.Backpack)
			{
				int meta = getBlockMetadata();
				if(meta == lvl)
				{
					setBlockMeta(meta + 1);
					Helper.getLogger().info("Applied upgrade "+upgradeSlot.getDisplayName());
					upgradeSlot = null;
					ItemStack[] isa = storageInventory;
					storageInventory = new ItemStack[getType().getStorageCount()];
					for(int i = 0; i < isa.length; i++)
						storageInventory[i] = isa[i];
					
					return true;
				}
				return false;
			}
			int typeIndex = type.ordinal(),
				currentLvl = componentInventory[typeIndex] == null ? -1 : componentInventory[typeIndex].getItemDamage();
			//if the user is upgrading one of their components and the upgrade levels match
			if(currentLvl + 1 == lvl)
			{
				if(componentInventory[typeIndex] == null)
					componentInventory[typeIndex] = new ItemStack(type.getBlockContainer());
				componentInventory[typeIndex].setItemDamage(lvl);
				if(componentInventory[typeIndex].stackTagCompound == null)
					componentInventory[typeIndex].stackTagCompound = new NBTTagCompound();
				upgradeSlot = null;
				return true;
			}
			return false;
		}
		else if(Block.getBlockFromItem(upgradeSlot.getItem()) instanceof BlockCampComponent)
		{
			BlockCampComponent block = (BlockCampComponent) Block.getBlockFromItem(upgradeSlot.getItem());
			int index = block.getType().ordinal();
			if(upgradeSlot == null)
			{
				componentInventory[index] = upgradeSlot;
				Helper.getLogger().info("Applied existing component to empty slot");
				upgradeSlot = null;
				return true;
			}
			else
			{
				ItemStack temp = componentInventory[index];
				componentInventory[index] = upgradeSlot;
				upgradeSlot = temp;
				Helper.getLogger().info("Swapped upgrade component with existing item in slot");
				return true;
			}
			
		}
		Helper.getLogger().warn("Had an invalid upgrade in the upgrade slot of a backpack which should not be there.");
		return false;
	}

	public ItemStack getComponentForDropping(UpgradeType componentType)
	{
		if (componentType == null)
		{
			Helper.getLogger().warn("Unexpected null value in TileEntityBackpack");
			return null; //this shouldn't happen;
		}
		ItemStack itemStack = componentInventory[componentType.ordinal()];
		componentInventory[componentType.ordinal()] = null;
		return itemStack;
	}
	public void deployAll()
	{
		NBTTagCompound tag;
		for (int i = 0; i < componentInventory.length; i++)
			if(componentInventory[i] != null)
			{
				tag = componentInventory[i].stackTagCompound;
				if(!tag.getBoolean(NBTHelper.IS_DEPLOYED))
					toggle(i);
			}
	}
	public void recoverAll()
	{
		NBTTagCompound tag;
		for (int i = 0; i < componentInventory.length; i++)
			if(componentInventory[i] != null)
			{
				tag = componentInventory[i].stackTagCompound;
				if(tag.getBoolean(NBTHelper.IS_DEPLOYED))
					toggle(i);
			}
	}
	public void toggle(int slot)
	{
		ItemStack upgrade = componentInventory[slot];
		if (upgrade == null)
			return;
		NBTTagCompound comTag = upgrade.getTagCompound();

		int xoffset = comTag.getInteger(NBTHelper.XOFFSET);
		int yoffset = comTag.getInteger(NBTHelper.YOFFSET);
		int zoffset = comTag.getInteger(NBTHelper.ZOFFSET);

		//This shit here doesn't work at all; need to rewrite this function
		if (worldObj.setBlock(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset, 
				(Block) ItemUpgrade.getTypeFromDamage(upgrade.getItemDamage()).getBlockContainer()))
			worldObj.getTileEntity(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset).readFromNBT(comTag);
	}

	public void setBlockMeta(int meta)
	{
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
	}
}
