package net.firesquared.hardcorenomad.block.campcomponents;

import java.util.Random;

import net.firesquared.hardcorenomad.GUIHandler.GUIType;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.helpers.Helper;
import net.firesquared.hardcorenomad.helpers.enums.Blocks;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCampFire;
import net.firesquared.hardcorenomad.helpers.enums.Tiles;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCampFire(meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		//TileEntityCampFire tileEntityCampFire = (TileEntityCampFire)world.getTileEntity(x, y, z);

		//if (tileEntityCampFire != null)
		//{
			player.openGui(HardcoreNomad.instance, GUIType.CAMPFIRE_TILEENTITY.ID, world, x, y, z);
		//}

		return true;
	}

	@Deprecated
	public ItemStack packIntoItemStack(World world, int x, int y, int z)
	{
		ItemStack itemStack;
		itemStack = new ItemStack(Blocks.BLOCK_CAMPFIRE.getBlock());

		TileEntityCampFire tileEntityCampFire = Tiles.<TileEntityCampFire>getTileEntity(world, x, y, z);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		if (tileEntityCampFire == null) {
			Helper.getLogger().debug("===>>>> Tile Entity is null check your X Y Z <<<<======");
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

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.CAMPFIRE;
	}
}
