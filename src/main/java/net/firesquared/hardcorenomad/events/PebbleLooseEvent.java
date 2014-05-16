package net.firesquared.hardcorenomad.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class PebbleLooseEvent extends PlayerEvent
{
	public final ItemStack slingShot;
	public int charge;

	public PebbleLooseEvent(EntityPlayer player, ItemStack slingShot, int charge)
	{
		super(player);
		this.slingShot = slingShot;
		this.charge = charge;
	}
}
