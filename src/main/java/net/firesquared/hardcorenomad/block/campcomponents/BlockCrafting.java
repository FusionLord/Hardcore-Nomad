package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCrafting extends BlockCampComponent
{
	public BlockCrafting()
	{
		super(Material.wood);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX, float hitY, float hitZ, float unknown)
	{
		if(world.isRemote)
			return true;
		player.displayGUIWorkbench(x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCrafting();
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
}
