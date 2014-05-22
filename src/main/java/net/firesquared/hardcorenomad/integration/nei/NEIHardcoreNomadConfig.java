package net.firesquared.hardcorenomad.integration.nei;

import net.firesquared.hardcorenomad.client.gui.CraftingTableGUI;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.integration.nei.recipe.CampfireFuelRecipeHandler;
import net.firesquared.hardcorenomad.integration.nei.recipe.CampfireRecipeHandler;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;

/**
 * The mod needs to be compiled with nei, and codechickencore.
 * It does not, however need NEI to run. Notice, this class is not called anywhere.
 * Nei, on load, searches for any class named NEI**Config.class, and uses it. 
 *
 * @author alix_the_alicorn
 */
public class NEIHardcoreNomadConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		
		/**
		 * Nei "question mark" ghost items, and nei shift click question mark for nomad crafting table
		 */
		API.registerGuiOverlay(CraftingTableGUI.class, "crafting");
        API.registerGuiOverlayHandler(CraftingTableGUI.class, new DefaultOverlayHandler(), "crafting");
        
        /**
         * Make it so when a user requests "Iron ingot" recipe form nei, it will show smelting in furnace,
         * and in campfire. Also makes it so when use asks nei for uses of coal, it will show as fuel in
         * furnace, and in campfire.
         * 
         * Grabs fuels from the TileEntityCampFire class
         * Grabs recipes from the FurnaceRecipes class
         */
        API.registerRecipeHandler(new CampfireRecipeHandler());
        API.registerUsageHandler(new CampfireRecipeHandler());
        
        API.registerRecipeHandler(new CampfireFuelRecipeHandler());
        API.registerUsageHandler(new CampfireFuelRecipeHandler());
        
        /**
         * If you don't want something showing up in NEI, use something like this
         * 
         * API.hideItem(new ItemStack(Items.apple));
         * 
         * or
         * 
         * API.hideItem(new ItemStack(Item.getItemFromBlock(Blocks.acacia_stairs)));
         * 
         */
        
	}

	
	/**
	 * Pretty self explainatory two methods
	 */
	@Override
	public String getName() {
		return Helper.Strings.MOD_NAME;
	}

	@Override
	public String getVersion() {
		return Helper.Strings.MOD_ID;
	}

}
