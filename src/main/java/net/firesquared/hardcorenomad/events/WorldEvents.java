package net.firesquared.hardcorenomad.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.firesquared.hardcorenomad.configuration.MainConfiguration;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEvents
{
	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		Helper.getLogger().debug("World is loading...");
		if (MainConfiguration.CONFIG_NATURALREGENENABLED)
		{
			event.world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("naturalRegeneration", "false");
		}
	}
}