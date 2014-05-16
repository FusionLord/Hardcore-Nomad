package net.firesquared.hardcorenomad.item.backpacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.client.render.RenderBackPackArmor;
import net.firesquared.hardcorenomad.helpers.BackPackTypes;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class ItemBackPack extends ItemArmor
{
	private Block blockBackPack;
	
	protected static void invInitialize(ItemStack bag)
	{
		NBTTagCompound invListTag = new NBTTagCompound();
		bag.stackTagCompound.setTag("Inventory", invListTag);
	}
	
	public static ItemStack invGet(ItemStack bag, int index)
	{
		NBTTagCompound invListTag = bag.stackTagCompound.getCompoundTag("Inventory");
		return ItemStack.loadItemStackFromNBT(invListTag.getCompoundTag(String.valueOf(index)));
	}
	
	public static ItemStack invSet(ItemStack bag, int index, ItemStack is)
	{
		NBTTagCompound invListTag = bag.stackTagCompound.getCompoundTag("Inventory");
		ItemStack is2 = ItemStack.loadItemStackFromNBT(invListTag.getCompoundTag(String.valueOf(index)));
		is.writeToNBT(invListTag.getCompoundTag(String.valueOf(index)));
		return is2;
	}
	
	public static ItemStack invRemove(ItemStack bag, int index)
	{
		NBTTagCompound invListTag = bag.stackTagCompound.getCompoundTag("Inventory");
		ItemStack is2 = ItemStack.loadItemStackFromNBT(invListTag.getCompoundTag(String.valueOf(index)));
		invListTag.removeTag(String.valueOf(index));
		return is2;
	}

	public ItemBackPack(int renderID)
	{
		super(ArmorMaterial.CLOTH, renderID, 1);
		blockBackPack = Blocks.BLOCK_BACKPACK.getBlock();
	}

	public BackPackTypes getBackPackType()
	{
		return null;
	}

	public static <T> T getTileEntity(IBlockAccess access, int x, int y, int z, Class<T> clazz)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		return !clazz.isInstance(te) ? null : (T) te;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlock(x, y, z);

		if(block == net.minecraft.init.Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
		{
			side = 1;
		}
		else
			if(block != net.minecraft.init.Blocks.vine && block != net.minecraft.init.Blocks.tallgrass && block != net.minecraft.init.Blocks.deadbush)
			{
				if(side == 0)
				{
					--y;
				}
				if(side == 1)
				{
					++y;
				}
				if(side == 2)
				{
					--z;
				}
				if(side == 3)
				{
					++z;
				}
				if(side == 4)
				{
					--x;
				}
				if(side == 5)
				{
					++x;
				}
			}

		if(!player.canPlayerEdit(x, y, z, side, stack))
		{
			return false;
		}
		else
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
							{
								nbtTagCompound = new NBTTagCompound();
								tileEntityBackPack.writeToNBT(nbtTagCompound);
							}

							nbtTagCompound.setInteger("x", x);
							nbtTagCompound.setInteger("y", y);
							nbtTagCompound.setInteger("z", z);

							nbtTagCompound.setInteger("backPackType", getBackPackType().ordinal());

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