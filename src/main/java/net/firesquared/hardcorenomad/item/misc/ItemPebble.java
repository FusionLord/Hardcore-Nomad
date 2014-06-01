package net.firesquared.hardcorenomad.item.misc;

import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPebble extends Item
{
	public ItemPebble() {
		this.maxStackSize = 23;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--par1ItemStack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPebble(world, player));
		}

		return par1ItemStack;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{	}

}
