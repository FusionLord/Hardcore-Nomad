package net.firesquared.hardcorenomad.block;

import net.firesquared.hardcorenomad.helpers.CampFireTypes;
import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.firesquared.hardcorenomad.tile.TileEntityBackPack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockCampComponent extends BlockContainer
{
	
	public BlockCampComponent(Material mat)
	{
		super(mat);
	}

	public void Deploy(World world, int x, int y, int z, int side, int hitX, int hitY, int hitZ, ItemStack stack, EntityPlayer player, int upgradeLevel, TileEntityBackPack backPack) {}
}
