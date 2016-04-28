package net.fusionlord.hardcorenomad.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class TileEntityEnchantingTable extends TileEntityUpgradable implements ITickable
{
	public int tickCount;
	public int bookRotation, prevBookRotation;
	private Random rand = null;
	private float angle = 0f;

	@Override
	void readExtraData(NBTTagCompound compound)
	{

	}

	@Override
	void writeExtraData(NBTTagCompound compound)
	{

	}

	@Override
	public void update()
	{
		if (rand == null) rand = worldObj.rand;
		this.prevBookRotation = this.bookRotation;
		tickCount++;
		EntityPlayer entityplayer = this.worldObj.func_184137_a((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 0.5F), (double)((float)this.pos.getZ() + 0.5F), 3.0D, false);
		if (entityplayer != null)
		{
			double d0 = entityplayer.posX - (double)((float)this.pos.getX() + 0.5F);
			double d1 = entityplayer.posZ - (double)((float)this.pos.getZ() + 0.5F);
			angle = (float) MathHelper.atan2(d1, d0);
		}
		else
		{
			angle += 0.02F;
		}

		while (this.bookRotation >= (float)Math.PI)
		{
			bookRotation -= ((float)Math.PI * 2F);
		}

		while (this.bookRotation < -(float)Math.PI)
		{
			this.bookRotation += ((float)Math.PI * 2F);
		}

		while (angle >= (float)Math.PI)
		{
			angle -= ((float)Math.PI * 2F);
		}

		while (angle < -(float)Math.PI)
		{
			angle += ((float)Math.PI * 2F);
		}

		float f2;

		for (f2 = angle - this.bookRotation; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F))
		{
			;
		}

		while (f2 < -(float)Math.PI)
		{
			f2 += ((float)Math.PI * 2F);
		}

		this.bookRotation += f2 * 0.4F;
		++this.tickCount;
	}
}
