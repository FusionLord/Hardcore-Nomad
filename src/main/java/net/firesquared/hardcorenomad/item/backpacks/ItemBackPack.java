package net.firesquared.hardcorenomad.item.backpacks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.NBTHelper;
import net.firesquared.hardcorenomad.helpers.enums.BackPackType;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.helpers.enums.Items;
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
		setMaxDamage(3);
		setNoRepair();
	}

	@Override
	public void onCreated(ItemStack is, World world, EntityPlayer entityPlayer)
	{
		if(is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return false;
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		if (BackPackType.fromLevel(stack.getItemDamage()).hasArmorSlot)
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
	public double getDurabilityForDisplay(ItemStack stack)
	{
		if (BackPackType.fromLevel(stack.getItemDamage()).hasArmorSlot)
		{
			NBTTagCompound tag = stack.getTagCompound();
			ItemStack armor = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBTHelper.ARMORSLOT));
			return armor.getItemDamageForDisplay();
		}
		return this.getMaxDamage();
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
	{
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(itemStack.stackTagCompound == null)
			Items.ITEM_BACKPACK.item.onCreated(itemStack, world, player);
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
		boolean isServer = !world.isRemote;
		if(isServer)
			Helper.getNomadLogger().info("running onItemUse server side"); 
		if(is.stackTagCompound == null)
			Items.ITEM_BACKPACK.item.onCreated(is, world, player);
		if(player.isSneaking())
		{
			Helper.getNomadLogger().info("Player sneaking; exiting item use method"); 
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

		Block bbp = Blocks.BLOCK_BACKPACK.block;
		if(player.canPlayerEdit(x, y, z, side, is))
			if (world.setBlock(x, y, z, bbp, is.getItemDamage(), 3))
			{
				//int meta = bbp.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, is.getItemDamage());
				bbp.onBlockPlacedBy(world, x, y, z, player, is);
				bbp.onPostBlockPlaced(world, x, y, z, is.getItemDamage());
				world.playSoundEffect(x+.5f, y, z+.5f, bbp.stepSound.func_150496_b(), 
						bbp.stepSound.getVolume() / 2f + .5f, bbp.stepSound.getPitch() * .8f);
				TileEntityBackPack backPack = (TileEntityBackPack)world.getTileEntity(x, y, z);
				backPack.readExtraNBT(is.stackTagCompound);
				--is.stackSize;
				return true;
			}
			else
			{
				if(isServer)
					Helper.getNomadLogger().info("failed on setBlock"); 
			}
		else		
			if(isServer)
				Helper.getNomadLogger().info("failed on can edit"); 
		if(isServer)
			Helper.getNomadLogger().info("placement successful"); 
		return true;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (BackPackType type : BackPackType.values())
		{
			ItemStack is = new ItemStack(this, 1, type.ordinal());
			onCreated(is, null, null);
			list.add(is);
		}
	}
	
	
}
