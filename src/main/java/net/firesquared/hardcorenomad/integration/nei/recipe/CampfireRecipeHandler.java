package net.firesquared.hardcorenomad.integration.nei.recipe;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.firesquared.hardcorenomad.client.gui.CampFireGUI;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import codechicken.nei.ItemList;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CampfireRecipeHandler extends TemplateRecipeHandler
{
	
	public static final String fuelName = Helper.Strings.MOD_ID + "." + "campfireFuel";
	public static final String smeltingName = Helper.Strings.MOD_ID + "." + "campfireSmelting";
	
	public static final String displayNameRecipe = "container" + "." + Helper.Strings.MOD_ID + "." + "recipe" + "." + "campfire";
	public static final String displayNameFuel = "container" + "." + Helper.Strings.MOD_ID + "." + "fuel" + "." + "campfire";
	
    public class SmeltingPair extends CachedRecipe
    {
        public SmeltingPair(ItemStack ingred, ItemStack result) {
            ingred.stackSize = 1;
            this.ingred = new PositionedStack(ingred, 51, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
        }

        @Override
		public PositionedStack getResult() {
            return result;
        }

        @Override
		public PositionedStack getOtherStack() {
            return afuels.get((cycleticks / 48) % afuels.size()).stack;
        }

        PositionedStack ingred;
        PositionedStack result;
    }

    public static class FuelPair
    {
        public FuelPair(ItemStack ingred, int burnTime) {
            this.stack = new PositionedStack(ingred, 51, 42, false);
            this.burnTime = burnTime;
        }

        public PositionedStack stack;
        public int burnTime;
    }

    public static ArrayList<FuelPair> afuels;
    public static HashSet<Block> efuels;

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), fuelName));
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), smeltingName));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return CampFireGUI.class;
    }

    @Override
    public String getRecipeName() {
        return NEIClientUtils.translate(displayNameRecipe);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (afuels == null)
            findFuels();
        return super.newInstance();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(smeltingName) && getClass() == CampfireRecipeHandler.class) {//don't want subclasses getting a hold of this
            Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
            for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
                arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
        } else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
                arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals(fuelName) && getClass() == CampfireRecipeHandler.class)//don't want subclasses getting a hold of this
            loadCraftingRecipes(smeltingName);
        else
            super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
                SmeltingPair arecipe = new SmeltingPair(recipe.getKey(), recipe.getValue());
                arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
                arecipes.add(arecipe);
            }
    }

    @Override
    public String getGuiTexture() {
        return "textures/gui/container/furnace.png";
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }

    private static Set<Item> excludedFuels() {
        Set<Item> excludedFuels = new HashSet<Item>();
        excludedFuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
        excludedFuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
        excludedFuels.add(Item.getItemFromBlock(Blocks.standing_sign));
        excludedFuels.add(Item.getItemFromBlock(Blocks.wall_sign));
        excludedFuels.add(Item.getItemFromBlock(Blocks.wooden_door));
        excludedFuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
        return excludedFuels;
    }

    private static void findFuels() {
        afuels = new ArrayList<FuelPair>();
        Set<Item> excludedfuels = excludedFuels();
        for (ItemStack item : ItemList.items)
            if (!excludedfuels.contains(item.getItem())) {
                int burnTime = TileEntityCampFire.getItemBurnTime(item);
                if (burnTime > 0)
                    afuels.add(new FuelPair(item.copy(), burnTime));
            }
    }

    @Override
    public String getOverlayIdentifier() {
        return smeltingName;
    }
}
