package net.firesquared.hardcorenomad.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityBackPack extends TileEntity {
    protected int blockMeta;

    public TileEntityBackPack() {
        super();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packetUpdateTileEntity) {
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
        readFromNBT(packetUpdateTileEntity.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        blockMeta = tag.getInteger("blockMeta");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("blockMeta", blockMeta);
    }



    /**
     * This will set the block meta data to the tile entity
     * @param meta
     */
    public void setBlockMeta(int meta) {
        blockMeta = meta;
    }

    /**
     * Get the block meta data
     * @return
     */
    public int getBlockMeta() {
        return blockMeta;
    }


}