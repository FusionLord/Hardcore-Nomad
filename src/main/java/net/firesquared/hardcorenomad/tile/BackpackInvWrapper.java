package net.firesquared.hardcorenomad.tile;

import java.util.ArrayList;

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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants.NBT;

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
			this.componentInventory = new ArrayList<ItemStack>(copy.componentInventory);
			for(int i = 0; i < componentInventory.size(); i++)
				componentInventory.set(i, componentInventory.get(i).copy());
			while(componentInventory.contains(null))
				componentInventory.remove(null);
		}
		if(copy.upgradeSlot != null)
			upgradeSlot = copy.upgradeSlot.copy();
		if(copy.armorSlot != null)
			armorSlot = copy.armorSlot.copy();
	}
	public BackpackInvWrapper(BackPackType type, ItemStack[] storageInventory, 
			ArrayList<ItemStack> componentInventory, ItemStack upgradeSlot, ItemStack armorSlot)
	{
		this.type = type;
		this.storageInventory = storageInventory;
		this.componentInventory = componentInventory;
		this.upgradeSlot = upgradeSlot;
		this.armorSlot = armorSlot;
	}
	public ItemStack[] storageInventory;
	//slot for everything excluding the backpack upgrade
	public ArrayList<ItemStack> componentInventory = new ArrayList<ItemStack>();
	public ItemStack upgradeSlot;
	public ItemStack armorSlot;
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		boolean isArmor = type.hasArmorSlot;
		int[] indexes;
		
		if(isArmor)
			indexes = new int[]{
				0, storageInventory.length, 
				storageInventory.length + 1, 
				storageInventory.length + 2,
				storageInventory.length + 2 + componentInventory.size()};
		else
			indexes = new int[]{
					0, storageInventory.length, 
					storageInventory.length + 1, 
					storageInventory.length + 1 + componentInventory.size()};
		
		for(int i = 1; i < indexes.length; i++)
		{
			if (slot >= indexes[i-1] && slot < indexes[i])
			{
				switch(i)
				{
					case 1:
						return !(Block.getBlockFromItem(itemStack.getItem()) instanceof BlockBackPack);
					case 2:
						return item instanceof ItemUpgrade || Block.getBlockFromItem(item) instanceof BlockCampComponent;
					case 3:
						if(isArmor)
							return item instanceof ItemArmor && !(item instanceof ItemBackPack);
						//$FALL-THROUGH$
					case 4:
						return Block.getBlockFromItem(item) instanceof BlockCampComponent;
				}
			}
		}
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
		int[] indexes;
		if(isArmor)
			indexes = new int[]{
				0, storageInventory.length, 
				storageInventory.length + 1, 
				storageInventory.length + 2,
				storageInventory.length + 2 + componentInventory.size()};
		else
			indexes = new int[]{
					0, storageInventory.length, 
					storageInventory.length + 1, 
					storageInventory.length + 1 + componentInventory.size()};
		for(int i = 1; i < indexes.length; i++)
		{
			if (slot >= indexes[i-1] && slot < indexes[i])
			{
				switch(i)
				{
					case 1:
						return storageInventory[slot];
					case 2:
						return upgradeSlot;
					case 3:
						if(isArmor)
							return armorSlot;
						return componentInventory.get(slot - indexes[2]);
					case 4:
						return componentInventory.get(slot - indexes[3]);
				}
			}
		}
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
		int[] indexes;
		if(isArmor)
			indexes = new int[]{
				0, storageInventory.length, 
				storageInventory.length + 1, 
				storageInventory.length + 2,
				storageInventory.length + 2 + componentInventory.size()};
		else
			indexes = new int[]{
					0, storageInventory.length, 
					storageInventory.length + 1, 
					storageInventory.length + 1 + componentInventory.size()};
		for(int i = 1; i < indexes.length; i++)
		{
			if (slot >= indexes[i-1] && slot < indexes[i])
			{
				switch(i)
				{
					case 1:
						storageInventory[slot] = itemStack;
						return;
					case 2:
						upgradeSlot = itemStack;
						return;
					case 3:
						if(isArmor)
							armorSlot = itemStack;
						else
							if(itemStack == null)
								componentInventory.remove(slot - indexes[2]);
							else
								componentInventory.set(slot - indexes[2], itemStack);
						return;
					case 4:
						if(itemStack == null)
							componentInventory.remove(slot - indexes[3]);
						else
							componentInventory.set(slot - indexes[3], itemStack);
						return;
				}
			}
		}
	}
	@Override
	public void markDirty()
	{
		// TODO Auto-generated method stub
		
	}
	public static void readExtraNBT(NBTTagCompound tag, BackpackInvWrapper inv)
	{
		inv.type = BackPackType.fromLevel(tag.getByte(levelKey));
		
		NBTTagList comInvTag = tag.getTagList(NBTHelper.COMINV, NBT.TAG_COMPOUND);
		inv.componentInventory.clear();
		for (int i = 0; i < comInvTag.tagCount(); i++)
		{
			ItemStack is = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTagAt(i));
			if(is != null)
				inv.componentInventory.add(is);
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
	
	public static void writeExtraNBT(NBTTagCompound tag, BackpackInvWrapper inv)
	{
		tag.setByte(levelKey, (byte) inv.type.ordinal());
		NBTTagList comInvList = new NBTTagList();
		for(ItemStack is : inv.componentInventory)
		{
			if(is == null)
				continue;
			NBTTagCompound temp = new NBTTagCompound();
			is.writeToNBT(temp);
			comInvList.appendTag(temp);
		}
		tag.setTag(NBTHelper.COMINV, comInvList);
		
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
	
		Block b;
		Item item = upgradeSlot.getItem();
		if(item instanceof ItemUpgrade)
		{
			int dmg = upgradeSlot.getItemDamage();
			int lvl = ItemUpgrade.getLevelFromDamage(dmg);
			UpgradeType uType = ItemUpgrade.getTypeFromDamage(dmg);
			//if the user is upgrading their backpack in-place
			if(uType == UpgradeType.BACKPACK)
				return doBackpackUpgrade(lvl);
			
			//check if an upgrade of this type exists already
			int slotTgt = -1;
			for(int i = 0; i < componentInventory.size(); i++)
			{
				ItemStack is = componentInventory.get(i);
				if(is!=null && is.stackTagCompound != null)
				{
					if(Block.getBlockFromItem(is.getItem()) == (uType.blockContainer))
					{
						slotTgt = i;
						break;
					}
				}
			}
			
			if(!uType.isUpgradeSequential)
				return doNonSequentialUpgrade(slotTgt, lvl, uType);
			
			//if the upgrade type wasn't found and thus the upgrade could not be applied
			if(lvl != 0 && slotTgt == -1)
				return false;
			//if the upgrade would create a duplicate of that type
			if(lvl == 0 && slotTgt != -1)
				return false;
			
			//in the event there is no compoentent of this type
			if(slotTgt == -1)
			{
				ItemStack is = new ItemStack(uType.blockContainer);
				is.setItemDamage(lvl);
				is.stackTagCompound = new NBTTagCompound();
				componentInventory.add(is);
				upgradeSlot = null;
				return true;
			}
			//otherwise update the existing component if possible
			ItemStack is = componentInventory.get(slotTgt);
			if(lvl != is.getItemDamage() + 1)
				return false;
			is.setItemDamage(lvl);
			if(is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			componentInventory.set(slotTgt, is);
			upgradeSlot = null;
			return true;
		}
		else if((b = Block.getBlockFromItem(item)) instanceof BlockCampComponent)
			return doUpgradeWithExisting((BlockCampComponent) b);
		Helper.getNomadLogger().warn("Had an invalid upgrade in the upgrade slot of a backpack which should not be there.");
		return false;
	}
	private boolean doBackpackUpgrade(int lvl)
	{
		if(type.ordinal() != lvl)
			return false;
		
		Helper.getNomadLogger().info("Applied upgrade "+upgradeSlot.getDisplayName());
		upgradeSlot = null;
		commonUpgrade();
		return true;
	}
	private boolean doUpgradeWithExisting(BlockCampComponent block)
	{
		int existingUpgrade = -1;
		//Check if there's already an upgrade of this type
		for(int i = 0; i < componentInventory.size(); i++)
		{
			ItemStack is = componentInventory.get(i);
			if(is!=null && Block.getBlockFromItem(is.getItem()) == block)
			{
				existingUpgrade = i;
				break;
			}
		}
		
		if(existingUpgrade == -1)
		{
			componentInventory.add(upgradeSlot.copy());
			Helper.getNomadLogger().info("Applied existing component to empty slot");
			upgradeSlot = null;
			return true;
		}
		ItemStack temp = componentInventory.get(existingUpgrade);
		componentInventory.set(existingUpgrade, upgradeSlot);
		upgradeSlot = temp;
		Helper.getNomadLogger().info("Swapped upgrade component with existing item in slot");
		return true;
	}
	private boolean doNonSequentialUpgrade(int slotTgt, int lvl, UpgradeType uType)
	{
		ItemStack is = new ItemStack(uType.blockContainer);
		is.setItemDamage(lvl);
		is.stackTagCompound = new NBTTagCompound();
		if(slotTgt == -1)
		{
			componentInventory.add(is);
			upgradeSlot = null;
			return true;
		}
		ItemStack is2 = componentInventory.get(slotTgt);
		if(is2.getItemDamage() >= lvl)
		{
			Helper.getNomadLogger().info("Will not automatically replace a higher level component with lower level upgrade");
			return false;
		}
		upgradeSlot = is2.copy();
		componentInventory.set(slotTgt, is2);
		return true;
	}
}