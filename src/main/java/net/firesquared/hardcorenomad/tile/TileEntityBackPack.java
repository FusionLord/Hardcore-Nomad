

package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.client.gui.BackpackGUI;
import net.firesquared.hardcorenomad.container.BackpackContainer;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquaredcore.helper.Vector3n;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityBackPack extends TileEntityDeployableBase implements IInventory
{
	//DO NOT MAKE ASSUMPTIONS about the size of any inventory or the value of any index
	//get the value from where it is defined.  Don't just put the current value in as an integer
	protected BackpackInvWrapper inv;
	private boolean reopen = false;

	//CONSTRUCTORS
	public TileEntityBackPack()
	{
		super(null);
	}

	public TileEntityBackPack(int metadata)
	{
		super(null);
	}

	//NBT AND PERSISTENCE
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		if(reopen)
			tag.setBoolean("reopen", true);
		writeExtraNBT(tag);
		if(reopen)
		{
			//reopen();
			reopen = false;
		}
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
		if(tag.hasKey("reopen"))
			reopen = tag.getBoolean("reopen");
	}
	
	public void readExtraNBT(NBTTagCompound tag)
	{
		inv = new BackpackInvWrapper();
		BackpackInvWrapper.readExtraNBT(tag, inv);
	}
	
	//HELPERS
	public BackPackType getType()
	{
		return inv.type;
	}
	
	public void setBlockMeta(int meta)
	{
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
	}
	
	public ItemStack getComponentForDropping(UpgradeType componentType)
	{
		if (componentType == null)
		{
			Helper.getNomadLogger().warn("Unexpected null value in TileEntityBackpack");
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
	public boolean doUpgrade(EntityPlayer player)
	{
		if(inv.doServerUpgrade())
		{
			if(getCurrentLevel() != inv.type.ordinal())
			{
				reopen = true;
				setBlockMeta(inv.type.ordinal());
				markDirty();
				player.closeScreen();
				//player.openContainer = new BackpackContainer(player.inventory, this);
			}
			return true;
		}
		else
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
			Helper.getNomadLogger().error("Attempted to toggle component that " + (is == null ? "was null" : "had a missing tag"));
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
			Helper.getNomadLogger().error("Attempted to recover a camp component with no, or corrupt, location data");
			return false;
		}
		Block b = worldObj.getBlock(x, y, z);
		
		if(!blockInst.getClass().isInstance(b))
		{
			Helper.getNomadLogger().error("Found unexpected block at target location");
			return false;
		}
			
		TE tile = Tiles.<TE>getTileEntity(worldObj, x, y, z);
		if(tile == null)
		{
			Helper.getNomadLogger().error("Specified tile entity was not found at target location");
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
				Helper.getNomadLogger().warn("Failed to confirm block placement at ".concat(coords.toString()));
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
					Helper.getNomadLogger().warn("ItemStack is doesn't have an nbt tag");
					return false;
				}
			}
			else
			{
				Helper.getNomadLogger().warn("Failed to validate tile entity at ".concat(coords.toString()));
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
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
//		if(reopen)
//		{
//			Minecraft mc = Minecraft.getMinecraft();
//			reopen(mc.thePlayer);
//			reopen = false;
//		}
	}
		
	private void reopen(EntityPlayer player)
	{
		Helper.getNomadLogger().debug("called");
		player.closeScreen();
		player.openGui(HardcoreNomad.instance, GUIType.BACKPACK_TILEENTITY.ID, worldObj, xCoord, yCoord, zCoord);
	}

}
