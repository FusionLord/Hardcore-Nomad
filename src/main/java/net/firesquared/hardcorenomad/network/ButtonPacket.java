package net.firesquared.hardcorenomad.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.firesquared.hardcorenomad.item.ItemUpgrade;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

//FusionLord likes butts
public class ButtonPacket extends AbstractPacket
{
	int ID;
	int x,y,z;
	public ButtonPacket()
	{}
	
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
	static final int componentCount = ItemUpgrade.UpgradeType.values().length - 1;
	@Override
	public void handleServerSide(EntityPlayer entityPlayer)
	{
		TileEntity te = entityPlayer.worldObj.getTileEntity(x, y, z);
		if(te != null)
		{
			TileEntityBackPack backPack = (TileEntityBackPack)te;
			switch(ID)
			{
				case -1:
					backPack.doUpgrade();
					return;
				case 100:
					backPack.deployAll();
					return;
				case 101:
					backPack.recoverAll();
					return;
				default:
					if(ID<0||ID>=componentCount)
						backPack.toggle(ID);
			}
		}
	}
	
}
