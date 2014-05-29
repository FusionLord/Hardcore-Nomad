package net.firesquared.hardcorenomad.helpers;

import net.firesquared.hardcorenomad.network.SetOffsetPacket;

import org.apache.logging.log4j.Level;

import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.network.SyncPlayerPropertiesPacket;
import net.firesquaredcore.helper.Logger;
import net.firesquaredcore.network.AbstractPacket;
import net.firesquaredcore.network.PacketHandler;

public class Helper extends net.firesquaredcore.helper.Helper
{
	@SuppressWarnings("unchecked")
	private static final Class<? extends AbstractPacket>[] packets = new Class[]
			{
				SyncPlayerPropertiesPacket.class, 
				ButtonPacket.class,
				SetOffsetPacket.class,
			};
	public class Strings
	{
		//merging in the Reference class
		public static final String MOD_ID = "hardcorenomad";
		public static final String MOD_NAME = "Hardcore Nomad";
		public static final String VERSION_NUMBER = "0.0.1";
		public static final String CHANNEL_NAME = MOD_ID;
		public static final String SERVER_PROXY_CLASS = "net.firesquared.hardcorenomad.proxy.ServerProxy";
		public static final String CLIENT_PROXY_CLASS = "net.firesquared.hardcorenomad.proxy.ClientProxy";
	}
	public static final Logger hcnLogger = new Logger(Strings.MOD_NAME, true, Level.ALL);
	
	public static Logger getNomadLogger()
	{
		return hcnLogger;
	}

	public static final PacketHandler PACKET_HANDLER = 
			new PacketHandler(Strings.CHANNEL_NAME, packets);
	//end
	

}
