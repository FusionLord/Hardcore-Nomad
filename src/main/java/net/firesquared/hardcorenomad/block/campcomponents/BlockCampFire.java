


package net.firesquared.hardcorenomad.block.campcomponents;


import java.util.Random;

import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCampFire extends BlockCampComponent
{
	public BlockCampFire()
	{
		super(Material.rock);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeMetal);
		setLightLevel(.8f);
		needsRandomTick = true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCampFire();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
	{
		// TileEntityCampFire tileEntityCampFire =
		// (TileEntityCampFire)world.getTileEntity(x, y, z);
		
		// if (tileEntityCampFire != null)
		// {
		player.openGui(HardcoreNomad.instance, GUIType.CAMPFIRE_TILEENTITY.ID, world, x, y, z);
		// }
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote) return;
		TileEntityCampFire campfire = Tiles.<TileEntityCampFire> getTileEntity(world, x, y, z);
		if(campfire == null) return;
		
		int particles = campfire.isBurning() ? 4 : 8;
		for(int j = 0; j < particles; j++)
		{
			world.spawnParticle("flame", x + .5f + (world.rand.nextFloat() - .5f) / 5, y + .25f, z + .5f + (world.rand.nextFloat() - .5f) / 5,
					(world.rand.nextFloat() - .5f) / 15, world.rand.nextFloat() / 10, (world.rand.nextFloat() - .5f) / 15);
			world.spawnParticle("smoke", x + .5f + (world.rand.nextFloat() - .5f) / 5, y + .25f, z + .5f + (world.rand.nextFloat() - .5f) / 5,
					(world.rand.nextFloat() - .5f) / 15, world.rand.nextFloat() / 10, (world.rand.nextFloat() - .5f) / 15);
		}
	}
	
	@Override
	protected boolean has3dRender()
	{
		return true;
	}
	
	
}
