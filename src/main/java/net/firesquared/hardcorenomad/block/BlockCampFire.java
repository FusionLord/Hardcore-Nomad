package net.firesquared.hardcorenomad.block;

import java.util.List;
import java.util.Random;

import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.CampFireTypes;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.tile.TileEntityCampFire;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
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
		float pixel = 1.0f / 16;
		setBlockBounds(0.0f - 8*pixel, pixel/16, 0.0f - 8*pixel, 1.0f + 8*pixel, 1.0f, 1.0f + 8*pixel);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		TileEntityCampFire tileEntityCampFire = new TileEntityCampFire();
		//tileEntityCampFire.setBlockMeta(var2);
		return tileEntityCampFire;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		//TileEntityCampFire tileEntityCampFire = (TileEntityCampFire)world.getTileEntity(x, y, z);

		//if (tileEntityCampFire != null)
		//{
			player.openGui(HardcoreNomad.instance, 2, world, x, y, z);
		//}

		return true;
	}

	public CampFireTypes getCampFireType(World world, int x, int y, int z) {
		TileEntityCampFire tileEntityCampFire = Helper.getTileEntity(world, x, y, z, TileEntityCampFire.class);
		return tileEntityCampFire.getCampFireType();
	}

	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_CAMPFIRE.getBlock());

		TileEntityCampFire tileEntityCampFire = Helper.getTileEntity(world, x, y, z, TileEntityCampFire.class);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		if (tileEntityCampFire == null) {
			LogHelper.debug("===>>>> Tile Entity is null check your X Y Z <<<<======");
			return null;
		}
		tileEntityCampFire.writeToNBT(nbtTagCompound);

		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World w, int x, int y, int z, Random rand)
	{
		TileEntityCampFire t = null;
		if(w != null && w.getTileEntity(x, y, z) != null) {
			t = (TileEntityCampFire) w.getTileEntity(x, y, z);
		}
		
		if(t.isBurning()) {
			if(w.isRemote) {
				for(int j = 0; j < 6; j++)
				{
					w.spawnParticle("flame", x + .5f + (w.rand.nextFloat() - .5f)/5, y + .25f, z + .5f + (w.rand.nextFloat() - .5f)/5,
							(w.rand.nextFloat() - .5f)/15,  w.rand.nextFloat() / 10, (w.rand.nextFloat() - .5f)/15);
					w.spawnParticle("smoke", x + .5f + (w.rand.nextFloat() - .5f)/5, y + .25f, z + .5f + (w.rand.nextFloat() - .5f)/5,
							(w.rand.nextFloat() - .5f)/15,  w.rand.nextFloat() / 10, (w.rand.nextFloat() - .5f)/15);
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity e)
    {
		AxisAlignedBB axisAlignedBB1 = this.getCollisionBoundingBoxFromPool(w, x, y, z);

        if (axisAlignedBB1 != null && axisAlignedBB.intersectsWith(axisAlignedBB1))
        {
        	list.add(axisAlignedBB1);
        }
		
        TileEntityCampFire t = null;
		if(w != null && w.getTileEntity(x, y, z) != null) {
			t = (TileEntityCampFire) w.getTileEntity(x, y, z);
		}
		
		if(t.isBurning()) {
			//Sets e on fire for 5 seconds
			e.setFire(5);
		}
    }
}
