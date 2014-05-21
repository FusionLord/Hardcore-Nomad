

package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.network.BackpackTilePacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileEntityBackPack extends TileEntityDeployableBase implements IInventory
{
	protected ItemStack[] storageInventory;
	protected ItemStack[] componentInventory = new ItemStack[9];
	protected ItemStack upgradeSlot;
	protected ItemStack armorSlot;
	private boolean initialized = false;


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
		for (int i = 0; i < 9; i++)
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
	
	@SideOnly(Side.CLIENT)
	public void acceptPacket(BackpackTilePacket pkt)
	{
		this.armorSlot = pkt.armorSlot;
		this.upgradeSlot = pkt.upgradeSlot;
		this.componentInventory = pkt.componentInventory;
		this.storageInventory = pkt.componentInventory;
	}
	
	public BackpackTilePacket getPacket()
	{
		BackpackTilePacket pkt = new BackpackTilePacket();
		
		pkt.armorSlot = this.armorSlot;
		pkt.upgradeSlot = this.upgradeSlot;
		pkt.componentInventory = this.componentInventory;
		pkt.storageInventory = this.storageInventory;
		pkt.setCoords(xCoord,yCoord,zCoord);
		
		return pkt;
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
		for (int i = 0; i < 9; i++)
		{
			if(comInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				componentInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		storageInventory = new ItemStack[getType().getStorageCount()];
		for (int i = 0; i < storageInventory.length; i++)
		{
			if(stgInvTag.hasKey(NBTHelper.SLOT.concat(""+i)))
				storageInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
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
		boolean isArmor = getType().hasArmorSlot();
		if(itemStack != null)
			itemStack = itemStack.copy();
		
		if (slot < storageInventory.length)
			storageInventory[slot] = itemStack;
		if (slot >= storageInventory.length && slot < storageInventory.length + componentInventory.length)
			componentInventory[slot - storageInventory.length] = itemStack;
		if (slot == storageInventory.length + componentInventory.length)
			upgradeSlot = itemStack;
		if (slot == storageInventory.length + componentInventory.length + 1 && isArmor)
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
		if (slot < storageInventory.length)
			return true;
		if (slot == storageInventory.length && itemStack.getItem() instanceof ItemUpgrade)
			return true;
		if (slot == storageInventory.length + 1 && itemStack.getItem() instanceof ItemArmor && !(itemStack.getItem() instanceof ItemBackPack))
			return true;
		return false;
	}

	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}

	public void doUpgrade()
	{
		if (upgradeSlot == null)
			return;

		ItemUpgrade upgrade = (ItemUpgrade)upgradeSlot.getItem();

		NBTTagCompound tag = componentInventory[ItemUpgrade.getTypeFromDamage(upgradeSlot.getItemDamage()).ordinal()].getTagCompound();
		tag.setInteger(NBTHelper.CURRENTLEVEL, ItemUpgrade.getLevelFromDamage(upgradeSlot.getItemDamage()));
	}

	public ItemStack getComponentForDropping(UpgradeType componentType)
	{
		if (componentType == null)
		{
			LogHelper.warn("Unexpected null value in TileEntityBackpack");
			return null; //this shouldn't happen;
		}
		ItemStack itemStack = componentInventory[componentType.ordinal()];
		componentInventory[componentType.ordinal()] = null;
		return itemStack;
	}

	public void toggleAll()
	{
		for (int i = 0; i < 9; i++)
		{
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

		if (worldObj.setBlock(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset, 
				(Block) ItemUpgrade.getTypeFromDamage(upgrade.getItemDamage()).getBlockContainer()))
			worldObj.getTileEntity(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset).readFromNBT(comTag);
	}

	public void setBlockMeta(int meta)
	{
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!initialized)
		{
			Helper.PACKET_HANDLER.sendToDimension(getPacket(), worldObj.provider.dimensionId);
			initialized = true;
		}
	}
}
