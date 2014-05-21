package net.firesquared.hardcorenomad.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.*;

@ChannelHandler.Sharable
public class PacketHandler extends MessageToMessageCodec<FMLProxyPacket, AbstractPacket>
{
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private LinkedList<Class<? extends AbstractPacket>> packets = new LinkedList<Class<? extends AbstractPacket>>();
	private boolean isPostInitialised = false;

	public void registerPackets()
	{
		// registerPacket(Packet.class);
		registerPacket(SyncPlayerPropertiesPacket.class);
		registerPacket(ButtonPacket.class);
		registerPacket(BackpackTilePacket.class);
	}

	public boolean registerPacket(Class<? extends AbstractPacket> clazz)
	{
		if(this.packets.size() > 256)
		{
			return false;
		}
		if(this.packets.contains(clazz))
		{
			return false;
		}
		if(this.isPostInitialised)
		{
			return false;
		}
		this.packets.add(clazz);
		return true;
	}

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket abstractPacket, List<Object> out) throws Exception
	{
		ByteBuf buffer = Unpooled.buffer();
		Class<? extends AbstractPacket> clazz = abstractPacket.getClass();
		if(!this.packets.contains(abstractPacket.getClass()))
		{
			throw new NullPointerException("No Packet Registered for: "
					+ abstractPacket.getClass().getCanonicalName());
		}
		byte discriminator = (byte) this.packets.indexOf(clazz);
		buffer.writeByte(discriminator);
		abstractPacket.encodeInto(channelHandlerContext, buffer);
		FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), channelHandlerContext.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		out.add(proxyPacket);
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, FMLProxyPacket fmlProxyPacket, List<Object> out) throws Exception
	{
		ByteBuf payload = fmlProxyPacket.payload();
		byte discriminator = payload.readByte();
		Class<? extends AbstractPacket> clazz = this.packets.get(discriminator);
		if(clazz == null)
		{
			throw new NullPointerException("No packet registered for discriminator: " + discriminator);
		}

		AbstractPacket packet = clazz.newInstance();
		packet.decodeInto(channelHandlerContext, payload.slice());

		EntityPlayer player;
		switch(FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
				player = this.getClientPlayer();
				packet.handleClientSide(player);
				break;

			case SERVER:
				INetHandler netHandler = channelHandlerContext.channel().attr(NetworkRegistry.NET_HANDLER).get();
				player = ((NetHandlerPlayServer) netHandler).playerEntity;
				packet.handleServerSide(player);
				break;

			default:
		}
		out.add(packet);
	}

	public void initialise()
	{
		this.channels = NetworkRegistry.INSTANCE.newChannel(Reference.CHANNEL_NAME, this);
		registerPackets();
	}

	public void postInitialise()
	{
		if(this.isPostInitialised)
		{
			return;
		}

		this.isPostInitialised = true;

		Collections.sort(this.packets, new Comparator<Class<? extends AbstractPacket>>()
		{
			@Override
			public int compare(Class<? extends AbstractPacket> clazz1, Class<? extends AbstractPacket> clazz2)
			{
				int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
				if(com == 0)
				{
					com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
				}

				return com;
			}
		});
	}

	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	public void sendToAll(AbstractPacket message)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendTo(AbstractPacket message, EntityPlayerMP playerMP)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(playerMP);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToAllAround(AbstractPacket message, NetworkRegistry.TargetPoint point)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToDimension(AbstractPacket message, int dimensionId)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToServer(AbstractPacket message)
	{
		this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channels.get(Side.CLIENT).writeAndFlush(message);
	}
}
