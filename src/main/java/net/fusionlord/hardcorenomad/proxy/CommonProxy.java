package net.fusionlord.hardcorenomad.proxy;

import net.fusionlord.hardcorenomad.common.init.ModBlocks;
import net.fusionlord.hardcorenomad.common.init.ModEntities;
import net.fusionlord.hardcorenomad.common.init.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by FusionLord on 4/25/2016.
 */
public class CommonProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		ModBlocks.registerBlocks();
		ModItems.registerItems();
		ModEntities.registerEntities();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
