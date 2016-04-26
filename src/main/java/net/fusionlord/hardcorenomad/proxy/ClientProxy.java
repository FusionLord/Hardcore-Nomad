package net.fusionlord.hardcorenomad.proxy;

import net.fusionlord.hardcorenomad.init.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by FusionLord on 4/25/2016.
 */
public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		ModBlocks.registerRenders();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);

	}
}
