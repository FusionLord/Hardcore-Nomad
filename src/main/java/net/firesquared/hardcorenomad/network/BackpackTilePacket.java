package net.firesquared.hardcorenomad.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquaredcore.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class BackpackTilePacket extends AbstractPacket
{
	public ItemStack[] storageInventory;
	public ItemStack[] componentInventory;
	public ItemStack upgradeSlot;
	public ItemStack armorSlot;
	int x,y,z;
	
	@Override
	public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf bbuf)
	{
		try
		{
			bbuf.writeInt(x);
			bbuf.writeInt(y);
			bbuf.writeInt(z);
			bbuf.writeShort((short)storageInventory.length);
			bbuf.writeShort((short)componentInventory.length);
			byte flag = 0;
			if(upgradeSlot == null) flag += 1;
			if(armorSlot == null) flag += 2;
			bbuf.writeByte(flag);
			for(int i = 0; i < storageInventory.length; i++)
			{
				writeStackToBuffer(bbuf, storageInventory[i]);
			}
			for(int i = 0; i < componentInventory.length; i++)
			{
				writeStackToBuffer(bbuf, componentInventory[i]);
			}
			if(upgradeSlot != null)
				writeStackToBuffer(bbuf, upgradeSlot);
			if(armorSlot != null)
				writeStackToBuffer(bbuf, armorSlot);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf bbuf)
	{
		x = bbuf.readInt();
		y = bbuf.readInt();
		z = bbuf.readInt();
		storageInventory = new ItemStack[bbuf.readShort()];
		componentInventory = new ItemStack[bbuf.readShort()];
		byte flag = bbuf.readByte();
		try
		{
			for(int i = 0; i < storageInventory.length; i++)
			{
				storageInventory[i] = readItemStackFromBuffer(bbuf);
			}
			for(int i = 0; i < componentInventory.length; i++)
			{
				componentInventory[i] = readItemStackFromBuffer(bbuf);
			}
			if(flag%2==0)
				upgradeSlot = readItemStackFromBuffer(bbuf);
			if((flag/2)%2==0)
				armorSlot = readItemStackFromBuffer(bbuf);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleClientSide(EntityPlayer entityPlayer)
	{
		TileEntityBackPack bp = Tiles.<TileEntityBackPack>getTileEntity(entityPlayer.worldObj, x, y, z);
		if(bp==null)
			return;
		bp.acceptPacket(this);
	}
	
	@Override
	public void handleServerSide(EntityPlayer entityPlayer)
	{
		
	}

	public void setCoords(int xCoord, int yCoord, int zCoord)
	{
		x=xCoord;
		y=yCoord;
		z=zCoord;
	}
	
	private void writeStackToBuffer(ByteBuf bbuf, ItemStack is) throws IOException
	{
        if (is == null)
        {
            bbuf.writeShort(-1);
        }
        else
        {
        	bbuf.writeShort(Item.getIdFromItem(is.getItem()));
        	bbuf.writeByte(is.stackSize);
        	bbuf.writeShort(is.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (is.getItem().isDamageable() || is.getItem().getShareTag())
            {
                nbttagcompound = is.stackTagCompound;
            }

            writeNBTToBuffer(bbuf, nbttagcompound);
        }
	}
	
	private void writeNBTToBuffer(ByteBuf bbuf, NBTTagCompound tag) throws IOException
	{
        if (tag == null)
        {
        	bbuf.writeShort(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.compress(tag);
            bbuf.writeShort((short)abyte.length);
            bbuf.writeBytes(abyte);
        }
	}
	
    private NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf bbuf) throws IOException
    {
        short short1 = bbuf.readShort();

        if (short1 < 0)
        {
            return null;
        }
        else
        {
            byte[] abyte = new byte[short1];
            bbuf.readBytes(abyte);
            return CompressedStreamTools.decompress(abyte);
        }
    }

    public ItemStack readItemStackFromBuffer(ByteBuf bbuf) throws IOException
    {
        ItemStack itemstack = null;
        short short1 = bbuf.readShort();

        if (short1 >= 0)
        {
            byte b0 = bbuf.readByte();
            short short2 = bbuf.readShort();
            itemstack = new ItemStack(Item.getItemById(short1), b0, short2);
            itemstack.stackTagCompound = readNBTTagCompoundFromBuffer(bbuf);
        }

        return itemstack;
    }
	
}
