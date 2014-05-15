package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.potion.PotionEffectHealCooldown;
import net.firesquared.hardcorenomad.potion.PotionHealCooldown;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHealingHerb extends ItemFood {

    public ItemHealingHerb(int healAmount, float saturation, boolean isWolfFavoriteFood)
    {
        super(healAmount, saturation, isWolfFavoriteFood);
    }

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();

		Long healTime = nbtTagCompound.getLong("healTime");

		LogHelper.debug("====> " + healTime);

		if(!entityPlayer.isPotionActive(Potion.invisibility))
		{
			entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		}

		return itemStack;

	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		//if(!world.isRemote)
		//{
			entityPlayer.setHealth(entityPlayer.getHealth() + 4 < entityPlayer.getMaxHealth() ? entityPlayer.getHealth() + 4 : entityPlayer.getMaxHealth());

			NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
			nbtTagCompound.setLong("healTime", 6000);

			entityPlayer.writeEntityToNBT(nbtTagCompound);
		//}

		super.onFoodEaten(itemStack, world, entityPlayer);
	}

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 10;
    }


}
