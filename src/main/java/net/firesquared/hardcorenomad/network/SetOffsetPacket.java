package net.firesquared.hardcorenomad.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SetOffsetPacket extends AbstractPacket
{
	int x, y, z, itemIndex, xOffset, zOffset;
	public SetOffsetPacket()
	{}

	public SetOffsetPacket(int x, int y, int z, int itemIndex, int xOffset, int zOffset)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.itemIndex = itemIndex;
		this.xOffset = xOffset;
		this.zOffset = zOffset;
	}

	@Override
	public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		byteBuf.writeInt(x);
		byteBuf.writeInt(y);
		byteBuf.writeInt(z);
		byteBuf.writeInt(itemIndex);
		byteBuf.writeInt(xOffset);
		byteBuf.writeInt(zOffset);
	}

	@Override
	public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		x = byteBuf.readInt();
		y = byteBuf.readInt();
		z = byteBuf.readInt();
		itemIndex = byteBuf.readInt();
		xOffset = byteBuf.readInt();
		zOffset = byteBuf.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer entityPlayer)
	{}

	@Override
	public void handleServerSide(EntityPlayer entityPlayer)
	{
		TileEntityBackPack backPack = (TileEntityBackPack) entityPlayer.worldObj.getTileEntity(x, y, z);

//		backPack.setComponentOffset(xOffset, zOffset);
	}
}
