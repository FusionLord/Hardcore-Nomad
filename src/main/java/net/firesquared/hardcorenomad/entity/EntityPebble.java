package net.firesquared.hardcorenomad.entity;

import net.firesquared.hardcorenomad.potion.PotionHealCooldown;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPebble extends EntityThrowable
{
	public EntityPebble(World par1World)
	{
		super(par1World);
	}

	public EntityPebble(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
	}

	public EntityPebble(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition movingObjectPosition)
	{
		if (movingObjectPosition.entityHit != null)
		{
			byte damage = 1;

			movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)damage);

			if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase)movingObjectPosition.entityHit;
				entity.addPotionEffect((new PotionEffect(Potion.moveSlowdown.getId(), 50, 3)));
			}

		}

		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("cloud", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
