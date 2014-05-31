package net.firesquared.hardcorenomad.block.campcomponents;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityAnvil;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnvil extends BlockCampComponent
{

	public BlockAnvil()
	{
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAnvil();
	}

	@Override
	protected boolean has3dRender()
	{
		return true;
	}
	public static class ContainerAnvil extends ContainerRepair
	{
		public ContainerAnvil(InventoryPlayer playerInv, World world, int x, int y, int z)
		{
			super(playerInv, world, x, y, z, playerInv.player);
		}
	}
	public static class GUIAnvil extends GuiRepair
	{
		public GUIAnvil(InventoryPlayer invPlayer, World world, int x, int y, int z)
		{
			super(invPlayer, world, x, y, z);
		}
	}
}
