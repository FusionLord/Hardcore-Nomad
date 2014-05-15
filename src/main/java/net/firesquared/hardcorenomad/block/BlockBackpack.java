package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBackPack extends BlockContainer {
    //TODO: Figure out block icons for the backpack or if we are going to do something else

    public BlockBackPack() {
        super(Material.cloth);
        setHardness(1.0F);
        setResistance(10.0F);
        setStepSound(soundTypeCloth);
        //setTileEntity(TileEntityBackPack.class);
        setBlockTextureName(Reference.MOD_ID + ":" + getUnlocalizedName());
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        TileEntityBackPack tileEntityBackPack = new TileEntityBackPack();
        tileEntityBackPack.setBlockMeta(var2);
        return tileEntityBackPack;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        //TODO: Get TileEntity, get the type of backpack, and drop that...
        return Items.ITEM_BACKPACKBASIC.getItem();
    }
}
