package net.firesquared.hardcorenomad.helpers.enums;

import net.firesquared.hardcorenomad.block.BlockBackPack;
import net.firesquared.hardcorenomad.block.campcomponents.BlockAnvil;
import net.firesquared.hardcorenomad.block.campcomponents.BlockBedRoll;
import net.firesquared.hardcorenomad.block.campcomponents.BlockBrewing;
import net.firesquared.hardcorenomad.block.campcomponents.BlockCampFire;
import net.firesquared.hardcorenomad.block.campcomponents.BlockCobbleGenerator;
import net.firesquared.hardcorenomad.block.campcomponents.BlockCrafting;
import net.firesquared.hardcorenomad.block.campcomponents.BlockEnchantmentTable;
import net.firesquared.hardcorenomad.block.campcomponents.BlockStorage;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.StatCollector;

public enum Blocks
{
	// TODO: Add Blocks Here
	// Format: BLOCK_NAME("Block.Name", new BlockClass())
	// Format: BLOCK_NAME("Block.Name", new BlockClass(), CreativeTab.TabName)
	// Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class)
	// Format: BLOCK_NAME("Block.Name", new BlockClass(), ItemBlock.Class, CreativeTab.TabName)
	BLOCK_BACKPACK("backpack", new BlockBackPack()),
	BLOCK_CAMPFIRE("campfire", new BlockCampFire()),
	BLOCK_BEDROLL("bedroll", new BlockBedRoll()),
	BLOCK_CRAFTING("crafting", new BlockCrafting()),
	BLOCK_STORAGE("storage", new BlockStorage()),
	BLOCK_ENCHANTMENTTABLE("enchantmenttable", new BlockEnchantmentTable()),
	BLOCK_ANVIL("anvil", new BlockAnvil()),
	BLOCK_COBBLEGEN("cobblegen", new BlockCobbleGenerator()),
	BLOCK_BREWING("brewingstand", new BlockBrewing())
	;

	private final String internalName;
	private Block block;
	private Class<? extends ItemBlock> itemBlockClass;
	private CreativeTabs creativeTabs;

	Blocks(String internalName, Block block)
	{
		this(internalName, block, ItemBlock.class, null);
	}

	Blocks(String internalName, Block block, CreativeTabs creativeTabs)
	{
		this(internalName, block, ItemBlock.class, creativeTabs);
	}

	Blocks(String internalName, Block block, Class<? extends ItemBlock> itemBlockClass)
	{
		this(internalName, block, itemBlockClass, null);
	}

	Blocks(String internalName, Block block, Class<? extends ItemBlock> itemBlockClass, CreativeTabs creativeTabs)
	{
		this.internalName = internalName;
		this.block = block;
		this.itemBlockClass = itemBlockClass;
		this.creativeTabs = creativeTabs;
		block.setBlockName(Helper.MOD_ID + "." + internalName);
	}

	public String getInternalName()
	{
		return internalName;
	}

	public String getStatName()
	{
		return StatCollector.translateToLocal(block.getUnlocalizedName().replace("tile.", "block."));
	}

	public Block getBlock()
	{
		return block;
	}

	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return itemBlockClass;
	}

	public CreativeTabs getCreativeTabs()
	{
		return creativeTabs;
	}
}
