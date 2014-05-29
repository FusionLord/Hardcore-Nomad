package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.block.BlockBackPack;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
//get the value from where it is defined.  Don't just put the current value in as an integer
public class BackpackInvWrapper implements IInventory
{
	private static final String levelKey = "inventoryLevel";
	public BackPackType type;
	public BackpackInvWrapper()
	{
	}
	public BackpackInvWrapper(BackpackInvWrapper copy)
	{
		this.type = copy.type;
		if(copy.storageInventory != null)
		{
			this.storageInventory = copy.storageInventory.clone();
			for(int i = 0; i < storageInventory.length; i++)
				storageInventory[i] = storageInventory[i].copy();
		}
		if(copy.componentInventory != null)
		{
			this.componentInventory = copy.componentInventory.clone();
			for(int i = 0; i < componentInventory.length; i++)
				componentInventory[i] = componentInventory[i].copy();
		}
		if(copy.upgradeSlot != null)
			upgradeSlot = copy.upgradeSlot.copy();
		if(copy.armorSlot != null)
			armorSlot = copy.armorSlot.copy();
	}
	public BackpackInvWrapper(BackPackType type, ItemStack[] storageInventory, ItemStack[] componentInventory, ItemStack upgradeSlot, ItemStack armorSlot)
	{
		this.type = type;
		this.storageInventory = storageInventory;
		this.componentInventory = componentInventory;
		this.upgradeSlot = upgradeSlot;
		this.armorSlot = armorSlot;
	}
	public ItemStack[] storageInventory;
	//slot for everything excluding the backpack upgrade
	public ItemStack[] componentInventory = new ItemStack[ItemUpgrade.getCampComponentCount()];
	public ItemStack upgradeSlot;
	public ItemStack armorSlot;
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (slot < storageInventory.length)
			return !(Block.getBlockFromItem(itemStack.getItem()) instanceof BlockBackPack);
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			return Block.getBlockFromItem(item) instanceof BlockCampComponent;
		if (slot == storageInventory.length + componentInventory.length)
			return item instanceof ItemUpgrade || Block.getBlockFromItem(item) instanceof BlockCampComponent;
		if (slot == storageInventory.length + componentInventory.length + 1 && type.hasArmorSlot)
			return item instanceof ItemArmor && !(item instanceof ItemBackPack);
		return false;
	}
	
	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}
	
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
	public int getSizeInventory()
	{
		return type.storageTotal;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		//Helper.getLogger().info((worldObj.isRemote?"Client":"Server")+" getting slot contents at index " + slot);
		//TODO: change so that the last 3 levels can all mount some form of armor
		//improved limited to un-enchanted iron or leather armor only; possibly also wood armor from TC
		//advanced can have any kind of un-enchanted, vanilla armor
		//aromored has no restrictions, beyond that the item extend ItemArmor
		boolean isArmor = type.hasArmorSlot;

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
		setInventorySlotContents(slot, null);
		return isTemp;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		boolean isArmor = type.hasArmorSlot;
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
	public void markDirty()
	{
		// TODO Auto-generated method stub
		
	}
	public static void readExtraNBT(NBTTagCompound tag, BackpackInvWrapper inv)
	{
		inv.type = BackPackType.fromLevel(tag.getByte(levelKey));
		NBTTagCompound comInvTag = tag.getCompoundTag(NBTHelper.COMINV);
		inv.componentInventory = new ItemStack[ItemUpgrade.getCampComponentCount()];
		for (int i = 0; i < inv.componentInventory.length; i++)
		{
			if(comInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				inv.componentInventory[i] = 
					ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		inv.storageInventory = new ItemStack[inv.type.storageTotal];
		for (int i = 0; i < inv.storageInventory.length; i++)
		{
			if(stgInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				inv.storageInventory[i] = 
					ItemStack.loadItemStackFromNBT(stgInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		if(tag.hasKey(NBTHelper.UPGRADESLOT))
			inv.upgradeSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.UPGRADESLOT));
		if(inv.type.hasArmorSlot && tag.hasKey(NBTHelper.ARMORSLOT))
			inv.armorSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
	}
	
	public static void writeExtraNBT(NBTTagCompound tag, 
			BackpackInvWrapper inv)
	{
		tag.setByte(levelKey, (byte) inv.type.ordinal());
		NBTTagCompound comInvTag = new NBTTagCompound();
		for (int i = 0; i < inv.componentInventory.length; i++)
		{
			if(inv.componentInventory[i]!=null)
				comInvTag.setTag(NBTHelper.SLOT.concat(""+i), inv.componentInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.COMINV, comInvTag);
		
		NBTTagCompound _stgInvTag = new NBTTagCompound();
		int _stgInvSize = inv.storageInventory.length;
		for (int i = 0; i < _stgInvSize; i++)
		{
			if(inv.storageInventory[i]!=null)
				_stgInvTag.setTag(NBTHelper.SLOT.concat(""+i), inv.storageInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.STGINV, _stgInvTag);
		
		if(inv.upgradeSlot != null)
			tag.setTag(NBTHelper.UPGRADESLOT, inv.upgradeSlot.writeToNBT(new NBTTagCompound()));
		
		if(inv.type.hasArmorSlot && inv.armorSlot != null)
			tag.setTag(NBTHelper.ARMORSLOT, inv.armorSlot.writeToNBT(new NBTTagCompound()));
	}
	
	private void commonUpgrade()
	{
		type = type.next();
		NBTTagCompound tag = new NBTTagCompound();
		writeExtraNBT(tag, this);
		readExtraNBT(tag, this);
	}
	public boolean doServerUpgrade()
	{		
		if (upgradeSlot == null )
			return false;
	
		if(upgradeSlot.getItem() instanceof ItemUpgrade)
		{
			int dmg = upgradeSlot.getItemDamage();
			int lvl = ItemUpgrade.getLevelFromDamage(dmg);
			UpgradeType uType = ItemUpgrade.getTypeFromDamage(dmg);
			//if the user is upgrading their backpack in-place
			if(uType == UpgradeType.BACKPACK)
			{
				if(type.ordinal() == lvl)
				{
					Helper.getNomadLogger().info("Applied upgrade "+upgradeSlot.getDisplayName());
					upgradeSlot = null;
					commonUpgrade();
					return true;
				}
				return false;
			}
			int typeIndex = type.ordinal();
			int	currentLvl = componentInventory[typeIndex] == null ? -1 : componentInventory[typeIndex].getItemDamage();
			//if the user is upgrading one of their components and the upgrade levels match
			if(currentLvl + 1 == lvl)
			{
				if(componentInventory[typeIndex] == null)
					componentInventory[typeIndex] = new ItemStack(uType.blockContainer);
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
				Helper.getNomadLogger().info("Applied existing component to empty slot");
				upgradeSlot = null;
				return true;
			}
			ItemStack temp = componentInventory[index];
			componentInventory[index] = upgradeSlot;
			upgradeSlot = temp;
			Helper.getNomadLogger().info("Swapped upgrade component with existing item in slot");
			return true;
			
		}
		Helper.getNomadLogger().warn("Had an invalid upgrade in the upgrade slot of a backpack which should not be there.");
		return false;
	}
}