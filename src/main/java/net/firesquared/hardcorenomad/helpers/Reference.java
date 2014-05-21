package net.firesquared.hardcorenomad.helpers;

import net.firesquared.hardcorenomad.network.PacketHandler;

public class Reference
{
	public static final String MOD_ID = "hardcorenomad";
	public static final String MOD_NAME = "Hardcore Nomad";
	public static final String VERSION_NUMBER = "0.0.1";
	public static final String CHANNEL_NAME = MOD_ID;
	public static final String SERVER_PROXY_CLASS = "net.firesquared.hardcorenomad.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "net.firesquared.hardcorenomad.proxy.ClientProxy";

	public static final PacketHandler PACKET_HANDLER = new PacketHandler();
}
