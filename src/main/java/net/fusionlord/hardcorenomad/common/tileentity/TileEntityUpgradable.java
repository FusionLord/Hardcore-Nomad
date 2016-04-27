package net.fusionlord.hardcorenomad.common.tileentity;

import net.fusionlord.hardcorenomad.common.blocks.properties.EnumUpgrade;
import net.fusionlord.hardcorenomad.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityUpgradable extends TileEntity
{
	private EnumUpgrade upgrade;
	private EnumFacing facing;

	public TileEntityUpgradable(EnumFacing facing, EnumUpgrade upgrade)
	{
		this.facing = facing;
		this.upgrade = upgrade;
	}

	public TileEntityUpgradable()
	{
		this(EnumFacing.NORTH, EnumUpgrade.WOOD);
	}

	public EnumFacing getFacing()
	{
		return facing;
	}

	public void setFacing(EnumFacing facing)
	{
		this.facing = facing;
	}

	public EnumUpgrade getUpgrade()
	{
		return upgrade;
	}

	public void setUpgrade(EnumUpgrade upgrade)
	{
		this.upgrade = upgrade;
	}

	@Override
	public final void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		readData(compound);
		markDirty();
	}

	private final void readData(NBTTagCompound compound)
	{
		if (compound.hasKey("facing"))
		{
			facing = EnumFacing.values()[compound.getInteger("facing")];
		}
		if (compound.hasKey("upgrade"))
		{
			upgrade = EnumUpgrade.values()[compound.getInteger("upgrade")];
			LogHelper.info("Reading upgrade level: " + upgrade.getName());
		}
		readExtraData(compound);
	}

	abstract void readExtraData(NBTTagCompound compound);

	@Override
	public final void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		writeData(compound);
	}

	private final void writeData(NBTTagCompound compound)
	{
		if (facing != null)
		{
			compound.setInteger("facing", facing.ordinal());
		}
		if (upgrade != null)
		{
			compound.setInteger("upgrade", upgrade.ordinal());
			LogHelper.info("Saving upgrade level: " + upgrade.getName());
		}
		writeExtraData(compound);
	}

	abstract void writeExtraData(NBTTagCompound compound);

	@Override
	public Packet<?> getDescriptionPacket()
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeData(tagCompound);
		return new SPacketUpdateTileEntity(getPos(), 0, tagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readData(pkt.getNbtCompound());
	}
}
