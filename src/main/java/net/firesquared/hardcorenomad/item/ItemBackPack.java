package net.firesquared.hardcorenomad.item;

import net.firesquared.hardcorenomad.block.BlockBackPack;
import net.firesquared.hardcorenomad.block.Blocks;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemBackPack extends ItemArmor
{

	public ItemBackPack(int renderID) 
	{
		super(ArmorMaterial.CLOTH, renderID, 1);
	}


    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        LogHelper.debug("Side: " + side);
        int newX = x;
        int newY = y;
        int newZ = z;

        switch (side) {
            case 1:
                newY++;
                break;
            case 2:
                newZ--;
                break;
            case 3:
                newZ++;
                break;
            case 4:
                newX--;
                break;
            case 5:
                newX++;
                break;
            default:
                return false;
        }

        // ToDo: Verify that backpack can be placed down here...
        // ToDo: Return false if you cant place this down before the setblock...

        // Todo: Transfer nbt data from item to block
        world.setBlock(newX, newY, newZ, Blocks.BLOCK_BACKPACK.getBlock());
        // ToDo: Remove item from inventory

        return true;
    }
}
