package net.firesquared.hardcorenomad.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.firesquared.hardcorenomad.helpers.LogHelper;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.network.SyncPlayerPropertiesPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEvents
{
	@SubscribeEvent
	public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event)
	{

		if(event.entity != null)
		{
			NBTTagCompound nbtTagCompound = event.entity.getEntityData();
			if(nbtTagCompound != null)
			{
				if(nbtTagCompound.hasKey("healTime"))
				{
					Long healTime = nbtTagCompound.getLong("healTime");
					if(healTime > 0)
					{
						healTime--;
						nbtTagCompound.setLong("healTime", healTime);

						//Reference.PACKET_HANDLER.sendTo(new SyncPlayerPropertiesPacket((EntityPlayer)event.entity), (EntityPlayerMP)event.entity);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerConnects(EntityJoinWorldEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			if(!event.world.isRemote)
			{
				Reference.PACKET_HANDLER.sendTo(new SyncPlayerPropertiesPacket((EntityPlayer) event.entity), (EntityPlayerMP) event.entity);
			}
		}
	}
}
