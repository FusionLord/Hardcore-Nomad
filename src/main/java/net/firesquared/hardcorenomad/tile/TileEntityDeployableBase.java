package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;;

public class TileEntityDeployableBase extends TileEntity
{
	private UpgradeType componentType;
	private TileEntityBackPack pack;
	private int xOffset;
	private int yOffset;
	private int zOffset;
	private int level;

	public TileEntityDeployableBase(UpgradeType componentType)
	{
		super();
		this.componentType = componentType;
	}

	public TileEntityDeployableBase(UpgradeType componentType, int metadata)
	{
		super();
		this.componentType = componentType;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		//tag.setInteger(NBTHelper.CURRENTLEVEL, currentLevel);
		if(componentType != null)
			tag.setInteger(NBTHelper.COMPONENTTYPE, componentType.ordinal());
		tag.setInteger(NBTHelper.OFFSET + NBTHelper.X, xOffset);
		tag.setInteger(NBTHelper.OFFSET + NBTHelper.Y, yOffset);
		tag.setInteger(NBTHelper.OFFSET + NBTHelper.Z, zOffset);
		tag.setByte("tileentitymetadata", (byte) getBlockMetadata());
		tag.setBoolean(NBTHelper.HAS_PARRENT_BACKPACK, pack != null);
		if(pack != null)
		{
			tag.setInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.X, pack.xCoord);
			tag.setInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.Y, pack.yCoord);
			tag.setInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.Z, pack.zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		//currentLevel = tag.getInteger(NBTHelper.CURRENTLEVEL);
		if(tag.hasKey(NBTHelper.COMPONENTTYPE))
			componentType = UpgradeType.values()[tag.getInteger(NBTHelper.COMPONENTTYPE)];
		else
			componentType = null;
		xOffset = tag.getInteger(NBTHelper.OFFSET + NBTHelper.X);
		yOffset = tag.getInteger(NBTHelper.OFFSET + NBTHelper.Y);
		zOffset = tag.getInteger(NBTHelper.OFFSET + NBTHelper.Z);
		level = tag.getByte("tileentitymetadata");
		if(tag.hasKey(NBTHelper.HAS_PARRENT_BACKPACK) && tag.getBoolean(NBTHelper.HAS_PARRENT_BACKPACK))
		{
			pack = Tiles.<TileEntityBackPack>getTileEntity(worldObj, 			
					tag.getInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.X),
					tag.getInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.Y),
					tag.getInteger(NBTHelper.PARRENT_BACKPACK_LOCATION + NBTHelper.Z));
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
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	public UpgradeType getComponentType()
	{
		return componentType;
	}

	public int getZOffset()
	{
		return zOffset;
	}

	public int getXOffset()
	{
		return xOffset;
	}

	public int getYOffset()
	{
		return yOffset;
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
