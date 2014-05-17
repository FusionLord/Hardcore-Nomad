package net.firesquared.hardcorenomad.item.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.firesquared.hardcorenomad.entity.EntitySlingShotPebble;
import net.firesquared.hardcorenomad.events.PebbleLooseEvent;
import net.firesquared.hardcorenomad.events.PebbleNockEvent;
import net.firesquared.hardcorenomad.item.Items;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemSlingShot extends Item
{
	public static final String[] slingShotIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public ItemSlingShot()
	{
		this.maxStackSize = 1;
		this.setMaxDamage(384);
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		PebbleNockEvent event = new PebbleNockEvent(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return event.result;
		}

		if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Items.ITEM_MISC_PEBBLE.getItem()))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	public int getItemEnchantability()
	{
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(this.getIconString() + "_standby");
		this.iconArray = new IIcon[slingShotIconNameArray.length];

		for (int i = 0; i < this.iconArray.length; ++i)
		{
			this.iconArray[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + slingShotIconNameArray[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1)
	{
		return this.iconArray[par1];
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int par4)
	{
		int j = this.getMaxItemUseDuration(itemStack) - par4;

		PebbleLooseEvent event = new PebbleLooseEvent(entityPlayer, itemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		j = event.charge;

		boolean flag = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

		if (flag || entityPlayer.inventory.hasItem(Items.ITEM_MISC_PEBBLE.getItem()))
		{
			float f = (float)j / 20.0F;
			f = (f * f + f * 20.F) / 3.0F;

			if ((double)f < 0.1D)
			{
				return;
			}

			if (f > 1.0F)
			{
				f = 1.0F;
			}

			EntitySlingShotPebble entitySlingShotPebble = new EntitySlingShotPebble(world, entityPlayer, f * 2.0F);

			if (f == 1.0F)
			{
				entitySlingShotPebble.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);

			if (k > 0)
			{
				entitySlingShotPebble.setDamage(entitySlingShotPebble.getDamage() + (double)k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

			if (l > 0)
			{
				entitySlingShotPebble.setKnockbackStrength(l);
			}

			itemStack.damageItem(1, entityPlayer);
			world.playSoundAtEntity(entityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (!flag)
			{
				entityPlayer.inventory.consumeInventoryItem(Items.ITEM_MISC_PEBBLE.getItem());
			}

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(entitySlingShotPebble);
			}
		}
	}
}
