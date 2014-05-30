package net.firesquared.hardcorenomad.block.campcomponents;

import java.util.Iterator;
import java.util.List;

import net.firesquared.hardcorenomad.block.BlockCampComponent;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.firesquared.hardcorenomad.tile.campcomponents.TileEntityBedRoll;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class BlockBedRoll extends BlockCampComponent
{

	public BlockBedRoll()
	{
		super(Material.cloth);
		this.setBlockBounds(-0.5f, 0f, 0f, 1.5f, .25f, 1f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityBedRoll();
	}

	@Override
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB boundingbox, List list,	Entity entity)
	{       
		AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(getBlockBoundsMinX() + x, getBlockBoundsMinY() + y, getBlockBoundsMinZ() + z,
				getBlockBoundsMaxX() + x, getBlockBoundsMaxY() + y, getBlockBoundsMaxZ() + z);

	    if (axisalignedbb1 != null && boundingbox.intersectsWith(axisalignedbb1))
	    {
	        list.add(axisalignedbb1);
	    }
	}
	
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hit_x, float hit_y, float hit_z, float someOtherFloat)
    {
        if (world.isRemote)
            return true;
        
        //int metaData = world.getBlockMetadata(x, y, z);
        
    	if(!world.provider.canRespawnHere())
    		return true;
    	
    	//ignoring this probably makes multiple players able to sleep in one bed, but
    	//we're using the metadata already, so we'll need to put some state information on the tile entity
        //if ((metaData & 4) != 0)
        //{
            EntityPlayer otherPlayer2 = null;
            Iterator<EntityPlayer> playerIterator = world.playerEntities.iterator();

            while (playerIterator.hasNext())
            {
                EntityPlayer otherPlayer = playerIterator.next();

                if (otherPlayer.isPlayerSleeping())
                {
                    ChunkCoordinates chunkcoordinates = otherPlayer.playerLocation;

                    if (chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z)
                    {
                        otherPlayer2 = otherPlayer;
                    }
                }
            }

            if (otherPlayer2 != null)
            {
                player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }

        //this keeps track of if another player is already in the bed
        //    func_149979_a(world, x, y, z, false);
        //}

        EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);

        if (enumstatus == EntityPlayer.EnumStatus.OK)
        	//this tracks that a player is in the bed
        	//func_149979_a(world, x, y, z, true);
            return true;
        
        if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)
            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE)
            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        return true;
    }

	@Override
	protected boolean has3dRender()
	{
		return true;
	}

	@Override
	protected List<ItemStack> getDrops(TileEntityDeployableBase deployableBase, int meta, List<ItemStack> list)
	{
		list.add(new ItemStack(this));
		return list;
	}
}
