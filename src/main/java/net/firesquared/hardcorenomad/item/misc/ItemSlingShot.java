package net.firesquared.hardcorenomad.item.misc;

import net.firesquared.hardcorenomad.entity.EntitySlingShotPebble;
import net.firesquared.hardcorenomad.events.PebbleLooseEvent;
import net.firesquared.hardcorenomad.events.PebbleNockEvent;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemSlingShot extends Item
{
//	public static final String[] slingShotIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
//	@SideOnly(Side.CLIENT)
//	private IIcon[] iconArray;

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
	public int getMaxItemUseDuration(ItemStack itemStack)
	{
		return 200;//let the player wind up for at most 10 seconds
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		PebbleNockEvent event = new PebbleNockEvent(player, itemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.ITEM_MISC_PEBBLE.item))
			player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));

		return itemStack;
	}

	@Override
	public int getItemEnchantability()
	{
		return 1;
	}
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{	}
	
	@Override
	public boolean isFull3D()
	{
		return true;
	}

//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		this.itemIcon = par1IconRegister.registerIcon(this.getIconString() + "_standby");
//		this.iconArray = new IIcon[slingShotIconNameArray.length];
//
//		for (int i = 0; i < this.iconArray.length; ++i)
//		{
//			this.iconArray[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + slingShotIconNameArray[i]);
//		}
//	}

//	@SideOnly(Side.CLIENT)
//	public IIcon getItemIconForUseDuration(int par1)
//	{
//		return this.iconArray[par1];
//	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int useDurration)
	{
		int charge = this.getMaxItemUseDuration(itemStack) - useDurration;

		PebbleLooseEvent event = new PebbleLooseEvent(player, itemStack, charge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		charge = event.charge;

		boolean infiniteAmmo = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

		if (infiniteAmmo || player.inventory.hasItem(Items.ITEM_MISC_PEBBLE.item))
		{
			float shotPower = Math.min(charge * (charge + 4) / 12F, 1);

			if (shotPower < 0.1D)
				return;

			EntitySlingShotPebble projectile = new EntitySlingShotPebble(world, player, shotPower * 2.0F);

			//give the shot a 25% crit chance at full power
			if (shotPower == 1.0F && world.rand.nextFloat() >= .75)
				projectile.setIsCritical(true);

			int powerEnchantLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
			if (powerEnchantLevel > 0)
				projectile.setDamage(projectile.getDamage() + ++powerEnchantLevel * 0.5);

			int punchEnchantLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
			if (punchEnchantLevel > 0)
				projectile.setKnockbackStrength(punchEnchantLevel);

			itemStack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + shotPower * 0.5F);

			if (!infiniteAmmo)
				player.inventory.consumeInventoryItem(Items.ITEM_MISC_PEBBLE.item);

			if (!world.isRemote)
				world.spawnEntityInWorld(projectile);
		}
	}
	
	@Override
		public EnumAction getItemUseAction(ItemStack is)
		{
			return EnumAction.bow;
		}
}
