package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
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
	
	public static class ContainerCrafting extends ContainerWorkbench
	{
		public ContainerCrafting(InventoryPlayer invPlayer, World world, int x, int y, int z)
		{
			super(invPlayer, world, x, y, z);
		}
	}
	
	public static class GUICrafting extends GuiCrafting
	{
		public GUICrafting(InventoryPlayer invPlayer, World world, int x, int y, int z)
		{
			super(invPlayer, world, x, y, z);
		}
	}
}
