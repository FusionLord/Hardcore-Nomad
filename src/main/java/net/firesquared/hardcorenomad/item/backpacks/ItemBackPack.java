package net.firesquared.hardcorenomad.item.backpacks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.client.render.RenderBackPackArmor;
import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemBackPack extends ItemArmor
{
	private static Block blockBackPack;

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		NBTTagCompound tag;
		int width = 0, height = 0;
		switch(getBackPackType().ordinal())
		{
			case 0:
				width = 2;
				height = 3;
				break;
			case 1:
				width = 3;
				height = 4;
				break;
			case 2:
				width = 3;
				height = 7;
				break;
			case 3:
				width = 4;
				height = 8;
				break;
			default:
				break;
		}
		tag = NBTBackedInventory.createNBTInventory(width*height+9, 64);
		tag.setString("owner", entityPlayer.getDisplayName());
		tag.setInteger("backPackType", getBackPackType().ordinal());

		tag.setInteger("width", width);
		tag.setInteger("height", height);
		tag.setInteger("div", width*height);
		
		itemStack.stackTagCompound = tag;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
	{
		if(itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("owner"))
		{
			String owner = itemStack.stackTagCompound.getString("owner");
			list.add("Owner: " + owner);
		}
	}

	public ItemBackPack(int renderID)
	{
		super(ArmorMaterial.CLOTH, renderID, 1);
		if(blockBackPack == null)
			blockBackPack = Blocks.BLOCK_BACKPACK.getBlock();
		
	}

	public abstract BackPackTypes getBackPackType();

	public static <T> T getTileEntity(IBlockAccess access, int x, int y, int z, Class<T> clazz)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		return !clazz.isInstance(te) ? null : (T) te;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking())
		{
			FMLNetworkHandler.openGui(par3EntityPlayer, HardcoreNomad.instance, 1, par2World, 0, 0, 0);
			return par1ItemStack;
		}
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, HardcoreNomad.instance, 1, world, x, y, z);
			return true;
		}

		Block block = world.getBlock(x, y, z);

		if(block == net.minecraft.init.Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
			side = 1;
		else
			if(block != net.minecraft.init.Blocks.vine && block != net.minecraft.init.Blocks.tallgrass && block != net.minecraft.init.Blocks.deadbush)
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
			return doPlacement(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	private boolean doPlacement(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(stack.stackSize == 0)
		{
			return false;
		}
		else
		{
			if(world.canPlaceEntityOnSide(blockBackPack, x, y, z, false, side, (Entity) null, stack))
			{
				int i1 = this.blockBackPack.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);

				if(world.setBlock(x, y, z, blockBackPack, i1, 3))
				{
					if(world.getBlock(x, y, z) == blockBackPack)
					{
						blockBackPack.onBlockPlacedBy(world, x, y, z, player, stack);
						blockBackPack.onPostBlockPlaced(world, x, y, z, i1);
					}

					TileEntityBackPack tileEntityBackPack = getTileEntity(world, x, y, z, TileEntityBackPack.class);
					if(tileEntityBackPack != null)
					{
						NBTTagCompound nbtTagCompound = stack.getTagCompound();
						if(nbtTagCompound == null)
							throw new IllegalStateException("Expected critical NBT data on backpack item");

						nbtTagCompound.setInteger("x", x);
						nbtTagCompound.setInteger("y", y);
						nbtTagCompound.setInteger("z", z);

						nbtTagCompound.setInteger("backPackType", getBackPackType().ordinal());

						//if(!nbtTagCompound.hasKey("owner")) {
						//	nbtTagCompound.setString("owner", player.getDisplayName());
						//}

						tileEntityBackPack.readFromNBT(nbtTagCompound);
					}

					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
							blockBackPack.stepSound.func_150496_b(), (blockBackPack.stepSound.getVolume() + 1.0F) / 2.0F,
							this.blockBackPack.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}
			}

			return true;
		}
	}

	protected abstract int getWeightCap();

	protected abstract int invSize();

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return new RenderBackPackArmor();
	}
}
