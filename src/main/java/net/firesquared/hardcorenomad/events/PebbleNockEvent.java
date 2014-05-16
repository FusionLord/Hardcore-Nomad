package net.firesquared.hardcorenomad.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class PebbleNockEvent extends PlayerEvent
{
	public ItemStack result;

	public PebbleNockEvent(EntityPlayer player, ItemStack result)
	{
		super(player);
		this.result = result;
	}
}
