package net.firesquared.hardcorenomad.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class SyncPlayerPropertiesPacket extends AbstractPacket
{
	private NBTTagCompound nbtTagCompound;

	public SyncPlayerPropertiesPacket() {

	}

	public SyncPlayerPropertiesPacket(EntityPlayer player) {
		nbtTagCompound = player.getEntityData();
	}


	@Override public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		ByteBufUtils.writeTag(byteBuf, nbtTagCompound);
	}

	@Override public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		nbtTagCompound = ByteBufUtils.readTag(byteBuf);
	}

	@Override public void handleClientSide(EntityPlayer entityPlayer)
	{
		entityPlayer.readEntityFromNBT(nbtTagCompound);

		entityPlayer.getEntityData().setInteger("healTime", nbtTagCompound.getInteger("healTime"));
	}

	@Override public void handleServerSide(EntityPlayer entityPlayer)
	{

	}
}
