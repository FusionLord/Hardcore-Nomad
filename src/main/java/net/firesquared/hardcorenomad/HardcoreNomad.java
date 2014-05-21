

package net.firesquared.hardcorenomad;

import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.Helper.Numeral;
import net.firesquared.hardcorenomad.proxy.IProxy;
import net.minecraft.world.World;

@Mod(modid = Helper.MOD_ID, name = Helper.MOD_NAME)
public class HardcoreNomad
{
	@Mod.Instance(Helper.MOD_ID)
	public static HardcoreNomad instance;

	@SidedProxy(clientSide = Helper.CLIENT_PROXY_CLASS, serverSide = Helper.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	// Debugging Logging, disable this for real builds
	public static boolean logDebug = true;

	private World world = null;

	// PreInit Events
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Shrek is love, Shrek is life");
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
		
		
//		System.out.println("Testing roman numerals");
//		boolean success = true;
//		Random rand = new Random();
//		for(int i = 0; i < 50; i++)
//		{
//			int n = rand.nextInt(3999), n1;
//			String s;
//			System.out.println(String.format("%S => %S => %S", n, s = Numeral.ToRoman(n), n1 = Numeral.Parse(s)));
//			success &= n1 == n;
//		}
//		System.out.println(success ? "Passed" : "Failed");
//		System.out.println();
	}
}
