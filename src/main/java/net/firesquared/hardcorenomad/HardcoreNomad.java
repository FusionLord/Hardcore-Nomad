

package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.proxy.IProxy;
import net.minecraft.world.World;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
public class HardcoreNomad
{
	@Mod.Instance(Reference.MOD_ID)
	public static HardcoreNomad instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	// Debugging Logging, disable this for real builds
	public static boolean logDebug = true;

	private World world = null;

	// PreInit Events
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{

	}

	// Init Events
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerTileEntities();
		proxy.registerEntities();

		proxy.registerWorldEvents();
		proxy.registerPlayerEvents();
		proxy.registerEvents();

		proxy.initPacketHandler();

		proxy.registerRecipes();

		proxy.registerDungeonLoot();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}

	// PostInit Events
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInitPacketHandler();
	}
}
