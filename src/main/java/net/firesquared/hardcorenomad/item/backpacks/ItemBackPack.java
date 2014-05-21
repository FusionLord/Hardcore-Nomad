package net.firesquared.hardcorenomad.item.backpacks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.BackPackType;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
	public void onCreated(ItemStack is, World world, EntityPlayer entityPlayer)
	{
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger(NBTHelper.CURRENTLEVEL, is.getItemDamage());
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		if (BackPackType.values()[stack.getItemDamage()%BackPackType.values().length].hasArmorSlot())
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
		if(itemStack.stackTagCompound == null)
			Items.ITEM_BACKPACK.getItem().onCreated(itemStack, world, player);
		if(player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, HardcoreNomad.instance, 1, world, 0, 0, 0);
		}
		return itemStack;
		//return super.onItemRightClick(itemStack, world, player);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(is.stackTagCompound == null)
			Items.ITEM_BACKPACK.getItem().onCreated(is, world, player);
		if(player.isSneaking())
		{
			return true;
		}

		Block block = world.getBlock(x, y, z);

		if(!block.isReplaceable(world, x, y, z))
		{
			ForgeDirection fd = ForgeDirection.values()[side];
			x+=fd.offsetX;
			y+=fd.offsetY;
			z+=fd.offsetZ;
		}

		Block bbp = Blocks.BLOCK_BACKPACK.getBlock();
		if(player.canPlayerEdit(x, y, z, side, is))
			if (world.setBlock(x, y, z, bbp, is.getItemDamage(), 3))
			{
				return true;
			}
		return true;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (BackPackType type : BackPackType.values())
		{
			list.add(new ItemStack(this, 0, type.ordinal()));
		}
	}
}
