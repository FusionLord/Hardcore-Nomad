package net.firesquared.hardcorenomad.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemHealingHerb extends ItemFood
{

	public ItemHealingHerb(int healAmount, float saturation, boolean isWolfFavoriteFood)
	{
		super(healAmount, saturation, isWolfFavoriteFood);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();

		if(nbtTagCompound.getInteger("healTime") <= 0)
		{
			entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		}
		else
		{
			if(!world.isRemote)
			{
				entityPlayer.addChatComponentMessage(
						new ChatComponentText("You can heal again in " + (nbtTagCompound.getInteger("healTime") / 20 + 1) + " second(s)"));
			}
		}

		return itemStack;

	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(!world.isRemote)
		{
			entityPlayer.setHealth(entityPlayer.getHealth() + 4 < entityPlayer.getMaxHealth() ? entityPlayer.getHealth() + 4 : entityPlayer.getMaxHealth());

			NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
			nbtTagCompound.setInteger("healTime", 6000);

			entityPlayer.writeEntityToNBT(nbtTagCompound);
		}

		super.onFoodEaten(itemStack, world, entityPlayer);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack)
	{
		return 20;
	}

}
