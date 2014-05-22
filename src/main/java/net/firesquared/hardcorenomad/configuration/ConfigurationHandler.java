package net.firesquared.hardcorenomad.configuration;

import java.io.File;

public class ConfigurationHandler
{
	public static void init(String configPath) {
		MainConfiguration.init(new File(configPath + "options.cfg"));
	}
}
