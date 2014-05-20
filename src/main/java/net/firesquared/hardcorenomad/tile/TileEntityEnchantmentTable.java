package net.firesquared.hardcorenomad.tile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnchantmentTable extends TileEntityDeployableBase
{
	protected int enchantmentTableType;
	private NBTTagCompound tagInv;

	public static final int ModelID = RenderingRegistry.getNextAvailableRenderId();

	public TileEntityEnchantmentTable()
	{
		super(ComponentType.ENCHANTINGTABLE);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		tagInv = new NBTTagCompound();
		writeToNBT(tagInv);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagInv);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packetUpdateTileEntity)
	{
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packetUpdateTileEntity.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		enchantmentTableType = tag.getInteger("enchantmentTableType");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("enchantmentTableType", enchantmentTableType);
	}
}
