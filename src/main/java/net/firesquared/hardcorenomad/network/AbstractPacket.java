package net.firesquared.hardcorenomad.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractPacket
{
	public abstract void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);

	public abstract void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);

	public abstract void handleClientSide(EntityPlayer entityPlayer);

	public abstract void handleServerSide(EntityPlayer entityPlayer);
}
