package net.firesquared.hardcorenomad.item;

import java.util.List;

import net.firesquared.hardcorenomad.block.campcomponents.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper.Numeral;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
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
		Anvil((BlockCampComponent)Blocks.BLOCK_ANVIL.getBlock(), 1),
		BedRoll((BlockCampComponent)Blocks.BLOCK_BEDROLL.getBlock(), 4),
		BrewingStand((BlockCampComponent)Blocks.BLOCK_BREWING.getBlock(), 1),
		CampFire((BlockCampComponent)Blocks.BLOCK_CAMPFIRE.getBlock(), 5),
		CobbleGen((BlockCampComponent)Blocks.BLOCK_COBBLEGEN.getBlock(), 1),
		Crafting((BlockCampComponent)Blocks.BLOCK_CRAFTING.getBlock(), 1),
		Enchanting((BlockCampComponent)Blocks.BLOCK_ENCHANTMENTTABLE.getBlock(), 5);
		
		BlockCampComponent block;
		byte levels;
		private UpgradeType(BlockCampComponent block, int levels)
		{
			this.block = block;
			this.levels = (byte) levels;
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
}
