package net.fusionlord.hardcorenomad.proxy;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.init.ModBlocks;
import net.fusionlord.hardcorenomad.init.ModEntities;
import net.fusionlord.hardcorenomad.init.ModItems;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		OBJLoader.INSTANCE.addDomain(ModInfo.ID);
		ModBlocks.registerRenders();
		ModBlocks.registerTESRS();
		ModItems.registerRenders();
		ModEntities.registerRenders();
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
