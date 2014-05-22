package net.firesquared.hardcorenomad.helpers;

import org.apache.logging.log4j.Level;

import net.firesquared.hardcorenomad.network.BackpackTilePacket;
import net.firesquared.hardcorenomad.network.ButtonPacket;
import net.firesquared.hardcorenomad.network.SyncPlayerPropertiesPacket;
import net.firesquared.firesquaredcore.helper.Logger;
import net.firesquared.firesquaredcore.network.AbstractPacket;
import net.firesquared.firesquaredcore.network.PacketHandler;

public class Helper extends net.firesquared.firesquaredcore.helper.Helper
{
	private static final Class<? extends AbstractPacket>[] packets = new Class[]
			{
				SyncPlayerPropertiesPacket.class, 
				ButtonPacket.class, 
				BackpackTilePacket.class
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
	private static final Logger logger = new Logger(Strings.MOD_NAME, true, Level.ALL);
	
	public static Logger getLogger()
	{
		return logger;
	}

	public static final PacketHandler PACKET_HANDLER = 
			new PacketHandler(Strings.CHANNEL_NAME, packets);
	//end
	

}
