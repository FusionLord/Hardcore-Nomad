


package net.firesquared.hardcorenomad.entity;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.firesquared.hardcorenomad.helpers.enums.Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class EntitySlingShotPebble extends Entity implements IProjectile
{
	private int field_145791_d = -1;
	private int field_145792_e = -1;
	private int field_145789_f = -1;
	private Block field_145790_g;
	private int inData;
	private boolean inGround;
	public int canBePickedUp;
	public int arrowShake;
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;
	private int knockbackStrength;
	
	public EntitySlingShotPebble(World par1World)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}
	
	public EntitySlingShotPebble(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}
	
	public EntitySlingShotPebble(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLivingBase;
		
		this.posY = par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() - 0.10000000149011612D;
		double d0 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
		double d1 = par3EntityLivingBase.boundingBox.minY + par3EntityLivingBase.height / 3.0F - this.posY;
		double d2 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		
		if(d3 >= 1.0E-7D)
		{
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(par2EntityLivingBase.posX + d4, this.posY, par2EntityLivingBase.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = (float) d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + f4, d2, par4, par5);
		}
	}
	
	public EntitySlingShotPebble(World world, EntityLivingBase shooter, float power)
	{
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = shooter;
		
		if(shooter instanceof EntityPlayer)
			this.canBePickedUp = 1;
		
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, power * 1.5F, 1.0F);
	}
	
	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.field_145791_d = par1NBTTagCompound.getShort("xTile");
		this.field_145792_e = par1NBTTagCompound.getShort("yTile");
		this.field_145789_f = par1NBTTagCompound.getShort("zTile");
		this.ticksInGround = par1NBTTagCompound.getShort("life");
		this.field_145790_g = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
		this.inData = par1NBTTagCompound.getByte("inData") & 255;
		this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
		
		if(par1NBTTagCompound.hasKey("damage", 99))
			this.damage = par1NBTTagCompound.getDouble("damage");
		
		if(par1NBTTagCompound.hasKey("pickup", 99))
			this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
		else if(par1NBTTagCompound.hasKey("player", 99))
			this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short) this.field_145791_d);
		par1NBTTagCompound.setShort("yTile", (short) this.field_145792_e);
		par1NBTTagCompound.setShort("zTile", (short) this.field_145789_f);
		par1NBTTagCompound.setShort("life", (short) this.ticksInGround);
		par1NBTTagCompound.setByte("inTile", (byte) Block.getIdFromBlock(this.field_145790_g));
		par1NBTTagCompound.setByte("inData", (byte) this.inData);
		par1NBTTagCompound.setByte("shake", (byte) this.arrowShake);
		par1NBTTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		par1NBTTagCompound.setByte("pickup", (byte) this.canBePickedUp);
		par1NBTTagCompound.setDouble("damage", this.damage);
	}
	
	@Override
	public void setThrowableHeading(double x, double y, double z, float speed, float variation)
	{
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= f2;
		y /= f2;
		z /= f2;
		x += this.rand.nextGaussian() * 0.0075 * variation;
		y += this.rand.nextGaussian() * 0.0075 * variation;
		z += this.rand.nextGaussian() * 0.0075 * variation;
		x *= speed;
		y *= speed;
		z *= speed;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / Math.PI);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int unused)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double velX, double velY, double velZ)
	{
		this.motionX = velX;
		this.motionY = velY;
		this.motionZ = velZ;
		
		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float magVel = MathHelper.sqrt_double(velX * velX + velZ * velZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(velX, velZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(velY, magVel) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}
	
	public void setIsCritical(boolean critical)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		
		if(critical)
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
		else
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
	}
	
	public boolean getIsCritical()
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		return (b0 & 1) != 0;
	}
	
	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}
	
	public void setKnockbackStrength(int knockback)
	{
		this.knockbackStrength = knockback;
	}
	
	public double getDamage()
	{
		return this.damage;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}
	
	public void setDamage(double hitpoints)
	{
		this.damage = hitpoints;
	}
	
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if(!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
		{
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;
			
			if(this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.ITEM_MISC_PEBBLE.item, 1)))
			{
				flag = false;
			}
			
			if(flag)
			{
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				par1EntityPlayer.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if(this.onGround)
		{
			EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ITEM_MISC_PEBBLE.item));
			worldObj.spawnEntityInWorld(entityItem);
			this.setDead();
			return;
		}
		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI);
		}
		
		Block block = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
		
		if(block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(
					this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
			
			if(axisalignedbb != null && 
					axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().
							getVecFromPool(this.posX, this.posY, this.posZ)))
				this.inGround = true;
		}
		
		if(this.arrowShake > 0)
			--this.arrowShake;
		
		if(this.inGround)
		{
			int j = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
			
			if(block == this.field_145790_g && j == this.inData)
			{
				++this.ticksInGround;
				
				if(this.ticksInGround == 1200)
					this.setDead();
			}
			else
			{
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			++this.ticksInAir;
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			if(movingobjectposition != null)
				vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
						movingobjectposition.hitVec.zCoord);
			
			Entity entity = null;
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ)
					.expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float gravityAccel;
			
			for(i = 0; i < list.size(); ++i)
			{
				Entity entity1 = list.get(i);
				
				if(entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
				{
					gravityAccel = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(gravityAccel, gravityAccel, gravityAccel);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
					
					if(movingobjectposition1 != null)
					{
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
						
						if(d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
			
			if(entity != null)
				movingobjectposition = new MovingObjectPosition(entity);
			
			if(movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
				
				if(entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer))
					movingobjectposition = null;
			}
			
			float velMag;
			float f4;
			
			if(movingobjectposition != null)
			{
				if(movingobjectposition.entityHit != null)
				{
					doEntityImpact(movingobjectposition);
				}
				else
				{
					this.field_145791_d = movingobjectposition.blockX;
					this.field_145792_e = movingobjectposition.blockY;
					this.field_145789_f = movingobjectposition.blockZ;
					this.field_145790_g = block;
					this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
					this.motionX = ((float) (movingobjectposition.hitVec.xCoord - this.posX));
					this.motionY = ((float) (movingobjectposition.hitVec.yCoord - this.posY));
					this.motionZ = ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
					velMag = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / velMag * 0.05000000074505806D;
					this.posY -= this.motionY / velMag * 0.05000000074505806D;
					this.posZ -= this.motionZ / velMag * 0.05000000074505806D;
					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);
					
					if(this.field_145790_g.getMaterial() != Material.air)
					{
						this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
					}
				}
			}
			
			if(this.getIsCritical())
				for(i = 0; i < 4; ++i)
					this.worldObj.spawnParticle("crit", this.posX + this.motionX * i / 4.0D, 
							this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 
							4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
			
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			velMag = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			
			while(this.rotationPitch - this.prevRotationPitch >= 180.0F)
				this.prevRotationPitch += 360.0F;
			while(this.rotationYaw - this.prevRotationYaw < -180.0F)
				this.prevRotationYaw -= 360.0F;
			while(this.rotationYaw - this.prevRotationYaw >= 180.0F)
				this.prevRotationYaw += 360.0F;
			
			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float drag = 0.99F;
			gravityAccel = 0.05F;
			
			if(this.isInWater())
			{
				for(int l = 0; l < 4; ++l)
				{
					f4 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4,
							this.motionX, this.motionY, this.motionZ);
				}
				this.motionY += .1f;
				drag = 1F;
			}
			
			this.motionX *= drag;
			this.motionY *= drag;
			this.motionZ *= drag;
			this.motionY -= gravityAccel;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.func_145775_I();
		}
	}

	private void doEntityImpact(MovingObjectPosition objPosition)
	{
		float velMag = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
		int finalDamage = MathHelper.ceiling_double_int(velMag * this.damage);
		
		if(this.getIsCritical()) finalDamage += this.rand.nextInt(finalDamage / 2 + 2);
		
		DamageSource damagesource = null;
		
		if(this.shootingEntity == null)
			// TODO: Damamge Stuff Here?
			damagesource = causePebbleDamage(this, this);
		else
			damagesource = causePebbleDamage(this, this.shootingEntity);
		
		if(objPosition.entityHit.attackEntityFrom(damagesource, finalDamage))
		{
			if(objPosition.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase entitylivingbase = (EntityLivingBase) objPosition.entityHit;
				
				if(this.knockbackStrength > 0)
				{
					float lateralSpeed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
					
					if(lateralSpeed > 0.0F)
					{
						objPosition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6 / lateralSpeed, 0.1,
								this.motionZ * this.knockbackStrength * 0.6 / lateralSpeed);
					}
				}
				
				if(this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
				{
					EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
					EnchantmentHelper.func_151385_b((EntityLivingBase) this.shootingEntity, entitylivingbase);
				}
				
				if(this.shootingEntity != null && objPosition.entityHit != this.shootingEntity
						&& objPosition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
					((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
			}
			
			this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			
			if(!(objPosition.entityHit instanceof EntityEnderman))
				this.setDead();
		}
		else
		{
			this.motionX *= -0.1;
			this.motionY *= -0.1;
			this.motionZ *= -0.1;
			this.rotationYaw += 180.0F;
			this.prevRotationYaw += 180.0F;
			this.ticksInAir = 0;
		}
	}
	
	public static DamageSource causePebbleDamage(EntitySlingShotPebble pebble, Entity target)
	{
		return (new EntityDamageSourceIndirect("pebble", pebble, target)).setProjectile();
	}
}
