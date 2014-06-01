package net.firesquared.hardcorenomad.item;

import java.util.List;
import java.util.Locale;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHealing extends ItemFood
{
	@SideOnly(Side.CLIENT)
	IIcon[] icons;
	public enum HealingType
	{
		PRIMITIVE(5 * 60 * 20, 3),
		FIRST_AID(12 * 60 * 20, 6),
		MAGICAL(30 * 60 * 20, 12)
		;
		
		public final int cooldownTicks, healAmountHP;
		public final String unlocalizedPostfix;
		private HealingType(int cooldownTicks, int healAmountHP)
		{
			this.cooldownTicks = cooldownTicks;
			this.healAmountHP = healAmountHP;
			this.unlocalizedPostfix = name().toLowerCase(Locale.ENGLISH).replaceAll("_", "");
		}
		public int getMeta()
		{
			return ordinal();
		}
		public static HealingType fromMeta(int meta)
		{
			HealingType[] types = values();
			if(meta < 0 || meta >= types.length)
				return null;
			return types[meta];
		}
	}

	public ItemHealing()
	{
		super(0, false);
		setMaxDamage(HealingType.values().length - 1);
		setMaxStackSize(4);
		setHasSubtypes(true);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
		int healTime = nbtTagCompound.getInteger("healTime") / 20 + 1;

		if(healTime <= 0)
			entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		else if(!world.isRemote)
			{
				String s = "You can heal again in " + healTime;
				s.concat(healTime == 1 ? " second." : " seconds.");
				entityPlayer.addChatComponentMessage(new ChatComponentText(s));
			}

		return itemStack;
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(!world.isRemote)
		{
			HealingType type = HealingType.fromMeta(itemStack.getItemDamage());
			
			entityPlayer.setHealth(Math.max(entityPlayer.getHealth() + type.healAmountHP, entityPlayer.getMaxHealth()));

			NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
			nbtTagCompound.setInteger("healTime", type.cooldownTicks);

			entityPlayer.writeEntityToNBT(nbtTagCompound);
		}

		super.onFoodEaten(itemStack, world, entityPlayer);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack)
	{
		return 15;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List itemstacks)
	{
		ItemStack is;
		for(HealingType type : HealingType.values())
		{
			is = new ItemStack(this, 1, type.ordinal());
			itemstacks.add(is);
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return false;
	}
	
	private final String unlocalizedPrefix = "item." + Helper.Strings.MOD_ID + ".healing.";
	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int dmg = is.getItemDamage();
		HealingType type = HealingType.fromMeta(dmg);
		return unlocalizedPrefix + type.unlocalizedPostfix;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		String s = getIconString();
		HealingType[] types = HealingType.values();
		icons = new IIcon[types.length];
		for(int i = 0; i < types.length; i++)
		{
			icons[i] = iconRegister.registerIcon(s.concat(new Integer(i).toString()));
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg)
	{
		return icons[dmg];
	}

}
