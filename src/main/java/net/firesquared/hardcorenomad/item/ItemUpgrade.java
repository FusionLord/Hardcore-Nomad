package net.firesquared.hardcorenomad.item;

import java.util.List;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.client.render.RenderCampComp;
import net.firesquared.hardcorenomad.client.render.backpack.RenderBackPack;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderAnvil;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBedRoll;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderBrewing;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCampfire;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCobbleGen;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderCrafting;
import net.firesquared.hardcorenomad.client.render.campcomponents.RenderEnchanting;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquaredcore.helper.Helper.Numeral;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ItemUpgrade extends Item
{
	private static ItemUpgrade instance;
	public static ItemStack getUpgradeStack(UpgradeType type, int level)
	{
		if(!type.isEnabled)
			return null;
		ItemStack is = new ItemStack(instance);
		if(level >= type.levels)
			throw new IllegalArgumentException();
		is.setItemDamage(type.ordinal() + 256 * level);
		return is;
	}
	public static UpgradeType getTypeFromDamage(int damage)
	{
		return UpgradeType.values()[damage % 256];
	}
	public static int getLevelFromDamage(int damage)
	{
		return (damage >> 8) % 256;
	}
	
	public enum UpgradeType
	{
		//NOTE: Changing the index of an item in this enum WILL BREAK saves!
		ANVIL((BlockCampComponent)Blocks.BLOCK_ANVIL.block, 
				new RenderAnvil(), 1, Tiles.ANVIL.tileClass),
		BEDROLL((BlockCampComponent)Blocks.BLOCK_BEDROLL.block, 
				new RenderBedRoll(), 4, Tiles.BEDROLL.tileClass),
		BREWING_STAND((BlockCampComponent)Blocks.BLOCK_BREWING.block, 
				new RenderBrewing(), 1, Tiles.BREWING_STAND.tileClass),
		CAMPFIRE((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.block, 
				new RenderCampfire(), 4, Tiles.CAMPFIRE.tileClass),
		COBBLE_GENERATOR((BlockCampComponent)Blocks.BLOCK_COBBLEGEN.block, 
				new RenderCobbleGen(), 1, Tiles.COBBLEGEN.tileClass),
		CRAFTING_TABLE((BlockCampComponent)Blocks.BLOCK_CRAFTING.block, 
				new RenderCrafting(), 1, Tiles.CRAFTING.tileClass),
		ENCHANTING_TABLE((BlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.block, 
				new RenderEnchanting(), 5, Tiles.ENCHANT_TABLE_COMPACT.tileClass),
		STORAGE(null, null, null, 1),
		
		BACKPACK(null, new RenderBackPack(), TileEntityBackPack.class, 3);
		
		public final boolean isEnabled;
		public final BlockCampComponent blockContainer;
		public final int levels;
		public final RenderCampComp combinedRenderer;
		public final Class<? extends TileEntityDeployableBase> tileEntityClass;
		@SuppressWarnings("unchecked")
		private UpgradeType(BlockCampComponent block, RenderCampComp renderer, int levels, Class<? extends TileEntity> clazz)
		{
			this(block, renderer, (Class<? extends TileEntityDeployableBase>)clazz, levels);
		}
		private UpgradeType(BlockCampComponent block, RenderCampComp renderer, Class<? extends TileEntityDeployableBase> clazz, int levels)
		{
			this.blockContainer = block;
			this.levels = levels;
			this.combinedRenderer = renderer;
			this.tileEntityClass = clazz;
			isEnabled = levels > 0 && (blockContainer != null || combinedRenderer != null || tileEntityClass != null);
		}
	}
	
	public ItemUpgrade()
	{
		instance = this;
		setMaxStackSize(1);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int dmg = itemStack.getItemDamage();
		UpgradeType ut = getTypeFromDamage(dmg);
		return ut.name().toLowerCase().replace('_', '.') + "." + Numeral.ToRoman(getLevelFromDamage(dmg) + 1) + ".Upgrade";
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{		
		for(UpgradeType ut : UpgradeType.values())
		{
			for(int i = 0; i < ut.levels; i++)
			{
				list.add(getUpgradeStack(ut, i));
			}
		}
	}
	
	public static int getCampComponentCount()
	{
		return UpgradeType.values().length - 1;
	}
}
