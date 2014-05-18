package net.firesquared.hardcorenomad.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//FusionLord likes butts
public class ButtonPacket extends AbstractPacket
{
	int ID;
	int x,y,z;
	public ButtonPacket()
	{
		
	}
	
	public ButtonPacket(int x, int y, int z, int buttonID)
	{
		ID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		byteBuf.writeInt(x);
		byteBuf.writeInt(y);
		byteBuf.writeInt(z);
		byteBuf.writeInt(ID);
		
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
	{
		x = byteBuf.readInt();
		y = byteBuf.readInt();
		z = byteBuf.readInt();
		ID = byteBuf.readInt();
	}
	
	@Override
	public void handleClientSide(EntityPlayer entityPlayer)
	{
		
	}
	
	@Override
	public void handleServerSide(EntityPlayer entityPlayer)
	{
		TileEntity te = entityPlayer.worldObj.getTileEntity(x, y, z);
		if(te != null)
		{
			TileEntityBackPack tebp = (TileEntityBackPack)te;
			if(ID >= 0 && ID < 9)
				tebp.toggle(ID);
			else
			{
				switch(ID)
				{
					case -1:
						tebp.deploy(-1);
					case -2:
						tebp.recover(-1);
					case -10:
						tebp.doUpgrade();
						return;
					default:
						return;
				}
			}
		}
	}
	
}
