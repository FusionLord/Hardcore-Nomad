package net.firesquared.hardcorenomad.block.campcomponents;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.item.ItemUpgrade.UpgradeType;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int meta, int side)
	{
		return Blocks.crafting_table.getIcon(meta, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (!world.isRemote)
		{
			entityPlayer.openGui(HardcoreNomad.instance, 3, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		TileEntityCrafting tileEntityCrafting = new TileEntityCrafting();
		return tileEntityCrafting;
	}

	@Override
	protected boolean has3dRender()
	{
		return false;
	}
	@Override
	public UpgradeType getType()
	{
		return UpgradeType.CRAFTING_TABLE;
	}
}
