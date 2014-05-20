package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.helpers.CampFireTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityCampFire extends TileEntityDeployableBase implements IInventory
{
	private NBTTagCompound tagInv;
	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	private CampFireTypes campFireType = CampFireTypes.TIER_1;

	public TileEntityCampFire()
	{
		super(ComponentType.CAMPFIRE);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		tagInv = new NBTTagCompound();
		writeToNBT(tagInv);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagInv);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packetUpdateTileEntity)
	{
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packetUpdateTileEntity.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList nbtTagList = tag.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbtTagList.tagCount(); i++)
		{
			NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
			byte b0 = nbtTagCompound.getByte("Slot");

			if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
			{
				this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
			}
		}

		furnaceBurnTime = tag.getShort("BurnTime");
		furnaceCookTime = tag.getShort("CookTime");
		currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
		campFireType = CampFireTypes.values()[tag.getInteger("campFireType")];
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
//		if(this.backPack != null)
//			super.writeToNBT(tag);
		tag.setShort("BurnTime", (short) furnaceBurnTime);
		tag.setShort("CookTime", (short)furnaceCookTime);
		tag.setInteger("campFireType", campFireType.ordinal());

		NBTTagList nbtTagList = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i)
		{
			if (this.furnaceItemStacks[i] != null)
			{
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setByte("Slot", (byte)i);
				this.furnaceItemStacks[i].writeToNBT(nbtTagCompound);
				nbtTagList.appendTag(nbtTagCompound);
			}
		}

		tag.setTag("Items", nbtTagList);
	}

	@Override
	public int getSizeInventory()
	{
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return this.furnaceItemStacks[var1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.furnaceItemStacks[par1] != null)
		{
			ItemStack itemStack;

			if (this.furnaceItemStacks[par1].stackSize <= par2)
			{
				itemStack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemStack;
			}
			else
			{
				itemStack = this.furnaceItemStacks[par1].splitStack(par2);

				if (this.furnaceItemStacks[par1].stackSize == 0)
				{
					this.furnaceItemStacks[par1] = null;
				}

				return itemStack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		if (this.furnaceItemStacks[var1] != null)
		{
			ItemStack itemStack = this.furnaceItemStacks[var1];
			this.furnaceItemStacks[var1] = null;
			return itemStack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		this.furnaceItemStacks[var1] = var2;

		if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
		{
			var2.stackSize = this.getInventoryStackLimit();
		}
	}

	// ToDo: check if this is needed?
	@Override
	public String getInventoryName()
	{
		return "container.campfire";
	}

	// ToDo: This too...
	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int var1) {
		return this.furnaceCookTime * var1 / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int var1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = 200;
		}

		return this.furnaceBurnTime * var1 / this.currentItemBurnTime;
	}

	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	@Override
	public void updateEntity()
	{
		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;

		if (this.furnaceBurnTime > 0)
		{
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.furnaceBurnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

				if (this.furnaceBurnTime > 0)
				{
					flag = true;

					if (this.furnaceItemStacks[1] != null)
					{
						--this.furnaceItemStacks[1].stackSize;

						if (this.furnaceItemStacks[1].stackSize == 0)
						{
							this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt())
			{
				//this.furnaceCookTime += 15; // ToDo: Increase Cook Rate...
				// Furnace Upgrades
				int Upgrade = (campFireType.ordinal() + 1) * 5 - 4;
				this.furnaceCookTime += Upgrade;

				if (this.furnaceCookTime >= 200)
				{
					this.furnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.furnaceCookTime = 0;
			}

			if (flag != this.furnaceBurnTime > 0)
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canSmelt()
	{
		if (this.furnaceItemStacks[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			if (itemstack == null) return false;
			if (this.furnaceItemStacks[2] == null) return true;
			if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;
			int result = furnaceItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[2].getMaxStackSize();
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[2] == null)
			{
				this.furnaceItemStacks[2] = itemstack.copy();
			}
			else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem())
			{
				this.furnaceItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
			}

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize <= 0)
			{
				this.furnaceItemStacks[0] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack itemStack)
	{
		int burnTime = net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack);

		return burnTime;
	}

	public static boolean isItemFuel(ItemStack itemStack)
	{
		boolean isFuel = net.minecraft.tileentity.TileEntityFurnace.isItemFuel(itemStack);

		return isFuel;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		return var1 == 2 ? false : (var1 == 1 ? isItemFuel(var2) : true);
	}

	public CampFireTypes getCampFireType() {
		return campFireType;
	}

	public void setCampFireType(CampFireTypes campFireType) {
		this.campFireType = campFireType;
	}
}
