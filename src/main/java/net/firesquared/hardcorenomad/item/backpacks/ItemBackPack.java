package net.firesquared.hardcorenomad.item.backpacks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemBackPack extends ItemArmor
{
	public ItemBackPack()
	{
		super(ArmorMaterial.CLOTH, 0, 1);
		hasSubtypes = true;
		setMaxDamage(0);
		setNoRepair();
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(NBTHelper.CURRENTLEVEL, itemStack.getItemDamage());
		itemStack.setTagCompound(tag);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		if (stack.getItemDamage() == BackPackType.BACKPACK_ARMORED.ordinal())
		{
			NBTTagCompound tag = stack.getTagCompound();
			ItemStack armor = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
			if(armor != null)
			{
				armor.setItemDamage(damage);
				tag.setTag(NBTHelper.ARMORSLOT, armor.writeToNBT(new NBTTagCompound()));
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
	{
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, HardcoreNomad.instance, 1, world, 0, 0, 0);
			return itemStack;
		}
		return super.onItemRightClick(itemStack, world, player);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, HardcoreNomad.instance, 1, world, x, y, z);
			return true;
		}

		if (world.isRemote)
			return true;

		Block block = world.getBlock(x, y, z);

		if(!block.isReplaceable(world, x, y, z))
		{
			if(side == 0)	--y;
			if(side == 1)	++y;
			if(side == 2)	--z;
			if(side == 3)	++z;
			if(side == 4)	--x;
			if(side == 5)	++x;
		}

		if(!player.canPlayerEdit(x, y, z, side, stack))
			return false;
		else
			if (world.setBlock(x, y, z, Blocks.BLOCK_BACKPACK.getBlock()))
			{
				TileEntityBackPack backPack = (TileEntityBackPack)world.getTileEntity(x, y, z);
				backPack.readExtraNBT(stack.getTagCompound());
				return true;
			}
		return false;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (BackPackType type : BackPackType.values())
		{
			ItemStack itemStack = new ItemStack(this, 0, type.ordinal());
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger(NBTHelper.CURRENTLEVEL, type.ordinal());
			itemStack.setTagCompound(tag);
			list.add(itemStack);
		}
	}
}
