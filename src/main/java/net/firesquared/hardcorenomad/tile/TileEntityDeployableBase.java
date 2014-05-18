package net.firesquared.hardcorenomad.tile;

import net.firesquared.hardcorenomad.helpers.TileEntityHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDeployableBase extends TileEntity
{
	protected TileEntityBackPack backPack;

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		int backPackX = tag.getInteger("backPackX");
		int backPackY = tag.getInteger("backPackY");
		int backPackZ = tag.getInteger("backPackZ");
		backPack = TileEntityHelper.getTileEntity(this.getWorldObj(), backPackX, backPackY, backPackZ, TileEntityBackPack.class);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("backPackX", backPack.xCoord);
		tag.setInteger("backPackY", backPack.yCoord);
		tag.setInteger("backPackZ", backPack.zCoord);
	}

	public TileEntityBackPack getBackPack() {
		return backPack;
	}

	public void setTileEntityBackPack(TileEntityBackPack tileEntityBackPack) {
		this.backPack = tileEntityBackPack;
	}
}
