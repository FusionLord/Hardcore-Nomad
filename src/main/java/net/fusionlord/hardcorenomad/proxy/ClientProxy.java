package net.fusionlord.hardcorenomad.proxy;

import net.fusionlord.hardcorenomad.ModInfo;
import net.fusionlord.hardcorenomad.client.init.ModBlocksRendering;
import net.fusionlord.hardcorenomad.common.init.ModBlocks;
import net.fusionlord.hardcorenomad.common.init.ModEntities;
import net.fusionlord.hardcorenomad.common.init.ModItems;
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
		ModBlocksRendering.registerRenders();
		ModBlocksRendering.registerTESRS();
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
