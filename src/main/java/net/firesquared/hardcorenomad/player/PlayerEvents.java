package net.firesquared.hardcorenomad.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.firesquared.hardcorenomad.item.Items;
import net.firesquared.hardcorenomad.lib.Reference;
import net.firesquared.hardcorenomad.network.SyncPlayerPropertiesPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

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

				NBTTagCompound nbtTagCompound = event.entity.getEntityData();
				if (!nbtTagCompound.hasKey("gotStarterBag")) {
					nbtTagCompound.setBoolean("gotStarterBag", true);

					EntityPlayer player = (EntityPlayer)event.entity;
					ItemStack itemStack = new ItemStack(Items.ITEM_BACKPACKBASIC.getItem(), 1);
					itemStack.getItem().onCreated(itemStack, event.world, (EntityPlayer) event.entity);

					player.inventory.addItemStackToInventory(itemStack);
				}
			}
		}
	}
}
