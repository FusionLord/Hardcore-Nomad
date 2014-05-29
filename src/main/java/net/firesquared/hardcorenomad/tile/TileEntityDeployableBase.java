package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;

public class TileEntityDeployableBase extends TileEntity
{
	private UpgradeType componentType;
	protected TileEntityBackPack pack;
	private int level;
	public boolean isDuplicate = false;

	public TileEntityDeployableBase(UpgradeType componentType)
	{
		super();
		this.componentType = componentType;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		if(componentType != null)
			tag.setInteger(NBTHelper.COMPONENTTYPE, componentType.ordinal());
		tag.setByte("tileentitymetadata", (byte) getBlockMetadata());
		tag.setBoolean(NBTHelper.HAS_PARRENT_BACKPACK, pack != null);
		if(pack != null)
			NBTHelper.setXYZ(tag, NBTHelper.PARRENT_BACKPACK_LOCATION, pack.xCoord, pack.yCoord, pack.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.blockMetadata = -1;
		super.readFromNBT(tag);
		if(tag.hasKey(NBTHelper.COMPONENTTYPE))
			componentType = UpgradeType.values()[tag.getInteger(NBTHelper.COMPONENTTYPE)];
		else
			componentType = null;
		level = tag.getByte("tileentitymetadata");
		
		if(tag.hasKey(NBTHelper.HAS_PARRENT_BACKPACK) && tag.getBoolean(NBTHelper.HAS_PARRENT_BACKPACK))
		{
			pack = Tiles.<TileEntityBackPack>getTileEntity(worldObj,
					NBTHelper.getXYZ(tag, NBTHelper.PARRENT_BACKPACK_LOCATION));
		}
	}

	public int getCurrentLevel()
	{
		if(worldObj == null)
			return level;
		return getBlockMetadata();
	}

	@Override
	public Packet getDescriptionPacket()
	{
		Helper.getNomadLogger().info("Preparing description packet");
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		Helper.getNomadLogger().info("Decoding description packet");
		readFromNBT(pkt.func_148857_g());
	}

	public UpgradeType getComponentType()
	{
		return componentType;
	}
	
	public void setParrent(TileEntityBackPack backpack)
	{
		if(backpack != null)
			pack = backpack;
	}
	
	public boolean hasParrent()
	{
		return pack != null && !(this instanceof TileEntityBackPack);
	}
	
	public TileEntityBackPack getParrent()
	{
		return pack;
	}
	
	public void clearParrent()
	{
		pack = null;
	}

}
