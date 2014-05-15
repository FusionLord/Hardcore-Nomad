package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.lib.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBackPack extends BlockContainer {
    //TODO: Figure out block icons for the backpack or if we are going to do something else

    public BlockBackPack() {
        super(Material.cloth);
        setHardness(1.0F);
        setResistance(10.0F);
        setStepSound(soundTypeCloth);

        //setTileEntity();
        setBlockTextureName(Reference.MOD_ID + ":" + getUnlocalizedName());
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return null;
    }
}
