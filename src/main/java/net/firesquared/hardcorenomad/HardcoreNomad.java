package net.firesquared.hardcorenomad;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.proxy.IProxy;
import net.firesquared.hardcorenomad.world.WorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
public class HardcoreNomad {
    @Mod.Instance(Reference.MOD_ID)
    public static HardcoreNomad instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    // Debugging Logging, disable this for real builds
    public static boolean logDebug = true;

    private World world = null;

    // PreInit Events
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    // Init Events
    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerBlocks();
        proxy.registerItems();
        proxy.registerTileEntities();

        proxy.registerWorldEvents();
		proxy.registerPlayerEvents();
    }

    // PostInit Events
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
