

package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityBackPack extends TileEntityDeployableBase implements IInventory
{
	//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
	//get the value from where it is defined.  Don't just put the current value in as an integer
	protected BackpackInvWrapper inv;


	//CONSTRUCTORS
	public TileEntityBackPack()
	{
		super(null);
		inv = new BackpackInvWrapper(getType());
	}

	public TileEntityBackPack(int metadata)
	{
		super(null, metadata);
		inv = new BackpackInvWrapper(getType());
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
		return BackPackType.values()[Math.max(0, 
				Math.min(getCurrentLevel(), 
						BackPackType.values().length-1))];
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
			if(type == UpgradeType.Backpack)
			{
				int meta = getBlockMetadata();
				if(meta == lvl)
				{
					setBlockMeta(meta + 1);
					Helper.getLogger().info("Applied upgrade "+inv.upgradeSlot.getDisplayName());
					inv.upgradeSlot = null;
					ItemStack[] isa = inv.storageInventory;
					inv.storageInventory = new ItemStack[getType().getStorageCount()];
					for(int i = 0; i < isa.length; i++)
						inv.storageInventory[i] = isa[i];
					
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


	public void deployAll()
	{
		NBTTagCompound tag;
		for (int i = 0; i < inv.componentInventory.length; i++)
			if(inv.componentInventory[i] != null)
			{
				tag = inv.componentInventory[i].stackTagCompound;
				if(!tag.getBoolean(NBTHelper.IS_DEPLOYED))
					toggle(i);
			}
	}
	public void recoverAll()
	{
		NBTTagCompound tag;
		for (int i = 0; i < inv.componentInventory.length; i++)
			if(inv.componentInventory[i] != null)
			{
				tag = inv.componentInventory[i].stackTagCompound;
				if(tag.getBoolean(NBTHelper.IS_DEPLOYED))
					toggle(i);
			}
	}
	public void toggle(int slot)
	{
		ItemStack upgrade = inv.componentInventory[slot];
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


}
