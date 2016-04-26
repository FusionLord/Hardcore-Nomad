package net.fusionlord.hardcorenomad;

import net.fusionlord.hardcorenomad.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by FusionLord on 4/25/2016.
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME)
public class HardcoreNomad
{
	@Mod.Instance(ModInfo.ID)
	public static HardcoreNomad INSTANCE;

	@SidedProxy(clientSide = "net.fusionlord.hardcorenomad.proxy.ClientProxy", serverSide = "net.fusionlord.hardcorenomad.proxy.CommonProxy")
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
