


package net.firesquared.hardcorenomad.tile.campcomponents;


import java.util.List;

import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//Derived from the vanilla brewing stand code
//Attempted to clean out the worst of the spaghetti code
public class TileEntityBrewingStand extends TileEntityDeployableBase implements ISidedInventory
{
	private static final int[] inputSlots = new int[] {3};
	private static final int[] outputSlots = new int[] {0, 1, 2};
	
	/** The ItemStacks currently placed in the slots of the brewing stand */
	private ItemStack[] brewingItemStacks = new ItemStack[4];
	private int brewTime;
	/**
	 * an integer with each bit specifying whether that slot of the stand
	 * contains a potion
	 */
	private int filledSlots;
	private Item ingredientID;
	private String customInventoryName;
	public TileEntityBrewingStand()
	{
		super(UpgradeType.BREWING_STAND);
	}
	
	private void brewPotions()
	{
		if(canBrew())
		{
			ItemStack itemstack = brewingItemStacks[3];
			
			for(int i = 0; i < 3; ++i)
				if(brewingItemStacks[i] != null && brewingItemStacks[i].getItem() instanceof ItemPotion)
				{
					int dmg1 = brewingItemStacks[i].getItemDamage();
					int dmg2 = modifyItemDamage(dmg1, itemstack);
					List list = Items.potionitem.getEffects(dmg1);
					List list1 = Items.potionitem.getEffects(dmg2);
					
					if((dmg1 <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null))
					{
						if(dmg1 != dmg2) brewingItemStacks[i].setItemDamage(dmg2);
					}
					else
						if(!ItemPotion.isSplash(dmg1) && ItemPotion.isSplash(dmg2)) brewingItemStacks[i].setItemDamage(dmg2);
				}
			
			if(itemstack.getItem().hasContainerItem(itemstack))
				brewingItemStacks[3] = itemstack.getItem().getContainerItem(itemstack);
			else
			{
				--brewingItemStacks[3].stackSize;
				
				if(brewingItemStacks[3].stackSize <= 0) brewingItemStacks[3] = null;
			}
			MinecraftForge.EVENT_BUS.post(new PotionBrewedEvent(brewingItemStacks));
		}
	}
	
	private boolean canBrew()
	{
		if(brewingItemStacks[3] != null && brewingItemStacks[3].stackSize > 0)
		{
			ItemStack itemstack = brewingItemStacks[3];
			
			if(!itemstack.getItem().isPotionIngredient(itemstack))
				return false;
			else
			{
				boolean flag = false;
				
				for(int i = 0; i < 3; ++i)
					if(brewingItemStacks[i] != null && brewingItemStacks[i].getItem() instanceof ItemPotion)
					{
						int j = brewingItemStacks[i].getItemDamage();
						int k = modifyItemDamage(j, itemstack);
						
						if(!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
						{
							flag = true;
							break;
						}
						
						List list = Items.potionitem.getEffects(j);
						List list1 = Items.potionitem.getEffects(k);
						
						if((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k)
						{
							flag = true;
							break;
						}
					}
				
				return flag;
			}
		}
		else
			return false;
	}
	
	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side)
	{
		return true;
	}
	
	/**
	 * Returns true if automation can insert the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side)
	{
		return isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public void closeInventory()
	{
	}
	
	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if(slot >= 0 && slot < brewingItemStacks.length)
		{
			ItemStack itemstack = brewingItemStacks[slot];
			brewingItemStacks[slot] = null;
			return itemstack;
		}
		else
			return null;
	}
	
	private int modifyItemDamage(int oldDamage, ItemStack is)
	{
		if(is == null)
			return oldDamage;
		if(is.getItem().isPotionIngredient(is))
			return PotionHelper.applyIngredient(oldDamage,
				is.getItem().getPotionEffect(is));
		else
			return oldDamage;
	}
	
	public void setInvName(String newName)
	{
		customInventoryName = newName;
	}
	
	@SideOnly(Side.CLIENT)
	public void setBrewTime(int brewTime)
	{
		this.brewTime = brewTime;
	}
	
	/**
	 * Returns an array containing the indices of the slots that can be accessed
	 * by automation on the given side of this block.
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int slotID)
	{
		return slotID == 1 ? inputSlots : outputSlots;
	}
	
	public int getBrewTime()
	{
		return brewTime;
	}
	
	/**
	 * Returns an integer with each bit specifying whether that slot of the
	 * stand contains a potion
	 */
	public int getFilledSlots()
	{
		int i = 0;
		
		for(int j = 0; j < 3; ++j)
			if(brewingItemStacks[j] != null) i |= 1 << j;
		
		return i;
	}
	
	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName()
	{
		return hasCustomInventoryName() ? customInventoryName : "container.brewing";
	}
	
	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return brewingItemStacks.length;
	}
	
	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return par1 >= 0 && par1 < brewingItemStacks.length ? brewingItemStacks[par1] : null;
	}
	
	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(par1 >= 0 && par1 < brewingItemStacks.length)
		{
			ItemStack itemstack = brewingItemStacks[par1];
			brewingItemStacks[par1] = null;
			return itemstack;
		}
		else
			return null;
	}
	
	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return customInventoryName != null && customInventoryName.length() > 0;
	}
	
	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		Item item = is.getItem();
		if(slot == 3)
			return item.isPotionIngredient(is);
		else
			return item instanceof ItemPotion ||
				item == Items.glass_bottle;
	}
	
	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false;
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory()
	{
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		brewingItemStacks = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			
			if(b0 >= 0 && b0 < brewingItemStacks.length) brewingItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		
		brewTime = tag.getShort("BrewTime");
		
		if(tag.hasKey("CustomName", 8)) customInventoryName = tag.getString("CustomName");
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int slotID, ItemStack is)
	{
		if(slotID >= 0 && slotID < brewingItemStacks.length) brewingItemStacks[slotID] = is;
	}
	
	@Override
	public void updateEntity()
	{
		if(brewTime > 0)
		{
			--brewTime;
			
			if(brewTime == 0)
			{
				brewPotions();
				markDirty();
			}
			else
				if(!canBrew())
				{
					brewTime = 0;
					markDirty();
				}
				else
					if(ingredientID != brewingItemStacks[3].getItem())
					{
						brewTime = 0;
						markDirty();
					}
		}
		else
			if(canBrew())
			{
				brewTime = 400;
				ingredientID = brewingItemStacks[3].getItem();
			}
		
		int i = getFilledSlots();
		
		if(i != filledSlots)
		{
			filledSlots = i;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
		}
		
		super.updateEntity();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("BrewTime", (short) brewTime);
		NBTTagList itemsList = new NBTTagList();
		
		for(int i = 0; i < brewingItemStacks.length; ++i)
			if(brewingItemStacks[i] != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				brewingItemStacks[i].writeToNBT(compound);
				itemsList.appendTag(compound);
			}
		
		tag.setTag("Items", itemsList);
		
		if(hasCustomInventoryName()) tag.setString("CustomName", customInventoryName);
	}
}
