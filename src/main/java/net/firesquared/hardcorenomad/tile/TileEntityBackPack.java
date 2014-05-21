

package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.backpacks.ItemBackPack;
import net.firesquared.hardcorenomad.item.upgrades.ItemUpgrade;
import net.firesquared.hardcorenomad.item.upgrades.UpgradeType;
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


	public TileEntityBackPack()
	{
		super(ComponentType.BACKPACK);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeExtraNBT(tag);
	}

	public void writeExtraNBT(NBTTagCompound tag)
	{
		// TODO: initialise all of this or crash
		NBTTagCompound comInvTag = new NBTTagCompound();
		for (int i = 0; i < 9; i++)
		{
			comInvTag.setTag(NBTHelper.SLOT.concat(""+i), componentInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.COMINV, comInvTag);
		NBTTagCompound stgInvTag = new NBTTagCompound();
		int stgInvSize = storageInventory.length;
		stgInvTag.setInteger(NBTHelper.STGINVSIZE, stgInvSize);
		for (int i = 0; i < stgInvSize; i++)
		{
			stgInvTag.setTag(NBTHelper.SLOT.concat(""+i), storageInventory[i].writeToNBT(new NBTTagCompound()));
		}
		tag.setTag(NBTHelper.STGINV, stgInvTag);
		tag.setTag(NBTHelper.UPGRADESLOT, upgradeSlot.writeToNBT(new NBTTagCompound()));
		tag.setTag(NBTHelper.ARMORSLOT, armorSlot.writeToNBT(new NBTTagCompound()));
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
			componentInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		NBTTagCompound stgInvTag = tag.getCompoundTag(NBTHelper.STGINV);
		int stgInvSize = stgInvTag.getInteger(NBTHelper.STGINVSIZE);
		storageInventory = new ItemStack[stgInvSize];
		for (int i = 0; i < stgInvSize; i++)
		{
			storageInventory[i] = ItemStack.loadItemStackFromNBT(comInvTag.getCompoundTag(NBTHelper.SLOT.concat(""+i)));
		}
		upgradeSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.UPGRADESLOT));
		armorSlot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
	}

	@Override
	public int getSizeInventory()
	{
		return 9 * (getCurrentLevel() + 1);
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		boolean isArmor = getCurrentLevel() == BackPackType.BACKPACK_ARMORED.ordinal();

		if (slot < storageInventory.length)
			return storageInventory[slot];
		if (slot == storageInventory.length)
			return upgradeSlot;
		if (slot == storageInventory.length + 1 && isArmor)
			return armorSlot;
		if (slot > storageInventory.length + 2)
			return componentInventory[slot - storageInventory.length - 2];
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int value)
	{
		ItemStack itemStack = getStackInSlot(slot);
		itemStack.stackSize -= value;
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		if (slot < storageInventory.length)
			storageInventory[slot] = itemStack;
		if (slot == storageInventory.length)
			upgradeSlot = itemStack;
		if (slot == storageInventory.length + 1 && getCurrentLevel() == 4)
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
		return false; // TODO: add ability to change the backpack's name in the anvil
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

		NBTTagCompound tag = componentInventory[upgrade.getUpgradeType().ordinal()].getTagCompound();
		tag.setInteger(NBTHelper.CURRENTLEVEL, ItemUpgrade.getUpgradeLevel(upgradeSlot.getItemDamage()));
	}

	public ItemStack getComponentForDropping(ComponentType componentType)
	{
		if (componentType == ComponentType.BACKPACK)
		{
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

		boolean deployed = comTag.getBoolean(NBTHelper.DEPLOYED);
		if (!deployed)
		{
			if(worldObj.setBlock(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset,
					getBlockFromComType(((ItemUpgrade) upgrade.getItem()).getUpgradeType())))
			{
				TileEntityDeployableBase deployableBase = (TileEntityDeployableBase) worldObj
						.getTileEntity(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset);
				comTag.setBoolean(NBTHelper.DEPLOYED, true);
				deployableBase.readFromNBT(comTag);
			}
		}
		else
		{
			comTag.setBoolean(NBTHelper.DEPLOYED, false);
			//TODO: read component NBT back to its item;
			worldObj.setBlockToAir(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset);
			worldObj.removeTileEntity(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset);
		}
	}

	private Block getBlockFromComType(UpgradeType upgradeType)
	{
		switch (upgradeType)
		{

			case ANVIL:
				return Blocks.BLOCK_ANVIL.getBlock();
			case BEDROLL:
				return Blocks.BLOCK_BEDROLL.getBlock();
			case BREWINGSTAND:
				return Blocks.BLOCK_BREWING.getBlock();
			case CAMPFIRE:
				return Blocks.BLOCK_CAMPFIRE.getBlock();
			case COBBLEGEN:
				return Blocks.BLOCK_COBBLEGEN.getBlock();
			case CRAFTINGTABLE:
				return Blocks.BLOCK_CRAFTING.getBlock();
			case ENCHANTINGTABLE:
				return Blocks.BLOCK_ENCHANTMENTTABLE.getBlock();
		}
		return net.minecraft.init.Blocks.air;
	}
}