

package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.firesquared.hardcorenomad.item.backpacks.BackPackInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBackPack extends TileEntity implements IInventory
{
	protected int blockMeta;
	protected int backPackType;
	protected BackPackInventory inventory;
	private NBTTagCompound tagInv;

	public static final int ModelID = RenderingRegistry.getNextAvailableRenderId();

	public TileEntityBackPack()
	{
		super();
	}

	@Override
	public Packet getDescriptionPacket()
	{
		tagInv = new NBTTagCompound();
		writeToNBT(tagInv);
		inventory = new BackPackInventory(tagInv);

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
		//blockMeta = tag.getInteger("blockMeta");
		backPackType = tag.getInteger("backPackType");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		//tag.setInteger("blockMeta", blockMeta);
		tag.setInteger("backPackType", backPackType);
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

	public void deploy(int iD)
	{
		if(iD == 0)
			for(int i = 1; i < 10; i++)
				deploy(i);
		else
		{
			//deploy/pack the upgrade with the specified index
		}
	}

}