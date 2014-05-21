package net.firesquared.hardcorenomad.block.campcomponents;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.HardcoreNomad;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityCrafting;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCrafting extends BlockCampComponent
{
	@SideOnly(Side.CLIENT)
	private IIcon field_150035_a;
	@SideOnly(Side.CLIENT)
	private IIcon field_150034_b;

	public BlockCrafting()
	{
		super(Material.wood);
		setHardness(1.0F);
		setResistance(100.0F);
		setStepSound(soundTypeWood);
		setBlockTextureName("crafting_table");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.field_150035_a : (p_149691_1_ == 0 ? net.minecraft.init.Blocks.planks.getBlockTextureFromSide(p_149691_1_) : (p_149691_1_ != 2 && p_149691_1_ != 4 ? this.blockIcon : this.field_150034_b));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.field_150035_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.field_150034_b = p_149651_1_.registerIcon(this.getTextureName() + "_front");
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
}
