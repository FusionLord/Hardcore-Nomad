package net.firesquared.hardcorenomad.configuration;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MainConfiguration
{
	public static boolean CONFIG_NATURALREGENENABLED = false;
	public static boolean CONFIG_WOODTOOLSDISABLED = true;
	public static boolean CONFIG_STONEBREAKDROPSPEBBLES = true;
	public static boolean CONFIG_COBBLEBREAKDROPSPEBBLES = true;
	public static boolean CONFIG_ALTRECIPEFORPEBBLES = false;

	public static final String CATNAME_HARDCORE = "hardcoresettings";

	private static Configuration configuration;

	protected static void init(File configFile)
	{
		configuration = new Configuration(configFile);

		configuration.load();

		configuration.addCustomCategoryComment(CATNAME_HARDCORE, "Hardcore Settings");
		CONFIG_NATURALREGENENABLED = configuration.get(CATNAME_HARDCORE, "NaturalRegenEnabled", false, "Should natural regeneration be on or off (True = on, False = off)").getBoolean(false);
		CONFIG_WOODTOOLSDISABLED = configuration.get(CATNAME_HARDCORE, "WoodToolsDisabled", true, "Should wood tools be disabled").getBoolean(true);
		CONFIG_STONEBREAKDROPSPEBBLES = configuration.get(CATNAME_HARDCORE, "StoneBreakDropsPebbles", true, "Should pebbles drop on Stone Break").getBoolean(true);
		CONFIG_COBBLEBREAKDROPSPEBBLES = configuration.get(CATNAME_HARDCORE, "CobbleBreakDropsPebbles", true, "Should pebbles drop on Cobblestone Break").getBoolean(true);
		CONFIG_ALTRECIPEFORPEBBLES = configuration.get(CATNAME_HARDCORE, "AltRecipeForPebbles", false, "Can you craft 9 pebbles from 1 piece of cobblestone").getBoolean(false);

		configuration.save();
	}
}
