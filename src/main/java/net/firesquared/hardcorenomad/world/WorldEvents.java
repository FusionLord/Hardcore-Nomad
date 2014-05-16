package net.firesquared.hardcorenomad.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEvents
{
	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		LogHelper.debug("World is loading...");

		World world = event.world;
		world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("naturalRegeneration", "false");
	}
}
