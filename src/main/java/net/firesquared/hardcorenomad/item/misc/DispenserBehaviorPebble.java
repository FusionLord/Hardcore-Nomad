package net.firesquared.hardcorenomad.item.misc;

import net.firesquared.hardcorenomad.entity.EntityPebble;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenserBehaviorPebble extends BehaviorProjectileDispense
{
	@Override
	protected IProjectile getProjectileEntity(World world, IPosition iPosition)
	{
		return new EntityPebble(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
	}
}
