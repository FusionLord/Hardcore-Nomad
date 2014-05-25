package net.firesquared.hardcorenomad.item;

import java.util.List;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquaredcore.helper.Helper.Numeral;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUpgrade extends Item
{
	private static ItemUpgrade instance;
	public static ItemStack getUpgradeStack(UpgradeType type, int level)
	{
		ItemStack is = new ItemStack(instance);
		if(level >= type.getMaxLevels())
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
		ANVIL((BlockCampComponent)Blocks.BLOCK_ANVIL.getBlock(), 1),
		BEDROLL((BlockCampComponent)Blocks.BLOCK_BEDROLL.getBlock(), 4),
		BREWING_STAND((BlockCampComponent)Blocks.BLOCK_BREWING.getBlock(), 1),
		CAMPFIRE((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 4),
		COBBLE_GENERATOR((BlockCampComponent)Blocks.BLOCK_COBBLEGEN.getBlock(), 1),
		CRAFTING_TABLE((BlockCampComponent)Blocks.BLOCK_CRAFTING.getBlock(), 1),
		ENCHANTING_TABLE((BlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.getBlock(), 5),
		STORAGE((BlockCampComponent)Blocks.BLOCK_STORAGE.getBlock(), 1),
		
		BACKPACK(null, 3);
		
		BlockCampComponent block;
		int levels;
		private UpgradeType(BlockCampComponent block, int levels)
		{
			this.block = block;
			this.levels = levels;
		}
		public BlockCampComponent getBlockContainer()
		{
			return block;
		}
		public int getMaxLevels()
		{
			return levels;
		}
	}
	
	public ItemUpgrade()
	{
		instance = this;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int dmg = par1ItemStack.getItemDamage();
		UpgradeType ut = getTypeFromDamage(dmg);
		return ut.name()+"."+Numeral.ToRoman(getLevelFromDamage(dmg)+1)+".Upgrade";
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{		
		for(UpgradeType ut : UpgradeType.values())
		{
			for(int i = 0; i < ut.getMaxLevels(); i++)
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
