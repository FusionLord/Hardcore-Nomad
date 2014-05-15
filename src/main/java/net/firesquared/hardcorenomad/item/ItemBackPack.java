package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.block.BlockBackPack;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ItemBackPack extends ItemArmor
{
    private Block blockBackPack;

	public ItemBackPack(int renderID) 
	{
		super(ArmorMaterial.CLOTH, renderID, 1);
        blockBackPack = Blocks.BLOCK_BACKPACK.getBlock();
	}


    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlock(x, y, z);

        if (block == net.minecraft.init.Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if (block != net.minecraft.init.Blocks.vine && block != net.minecraft.init.Blocks.tallgrass && block != net.minecraft.init.Blocks.deadbush)
        {
            if (side == 0) {
                --y;
            }
            if (side == 1) {
                ++y;
            }
            if (side == 2) {
                --z;
            }
            if (side == 3) {
                ++z;
            }
            if (side == 4) {
                --x;
            }
            if (side == 5) {
                ++x;
            }
        }

        if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else if (stack.stackSize == 0)
        {
            return false;
        }
        else
        {
            if (world.canPlaceEntityOnSide(blockBackPack, x, y, z, false, side, (Entity)null, stack))
            {
                int i1 = this.blockBackPack.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);

                if (world.setBlock(x, y, z, blockBackPack, i1, 3))
                {
                    if (world.getBlock(x, y, z) == blockBackPack)
                    {
                        blockBackPack.onBlockPlacedBy(world, x, y, z, player, stack);
                        blockBackPack.onPostBlockPlaced(world, x, y, z, i1);
                    }

                    // ToDo: transfer NBT data from item to block...

                    world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), blockBackPack.stepSound.func_150496_b(), (blockBackPack.stepSound.getVolume() + 1.0F) / 2.0F, this.blockBackPack.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }
            }

            return true;
        }

    }

}
