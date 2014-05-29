package net.firesquared.hardcorenomad.client.render.campcomponents;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.firesquared.hardcorenomad.client.render.RenderCampComp;
import net.firesquared.hardcorenomad.tile.TileEntityDeployableBase;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RenderBrewing extends RenderCampComp
{
	static final Map<World, RenderBlocks> map = new HashMap<World, RenderBlocks>();
	static
	{
		map.put(null, new RenderBlocks());
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item)
	{
		Tessellator.instance.startDrawingQuads();
		map.get(null).renderBlockAsItem(Blocks.brewing_stand, 0, 1.0f);
		Tessellator.instance.draw();
	}
	
	@Override
	protected void renderTile(TileEntityDeployableBase tile, int lighting)
	{	
		GL11.glTranslated(-tile.xCoord, -tile.yCoord, -tile.zCoord);
		RenderBlocks rb;
		World world = tile.getWorldObj();
		if(map.containsKey(world))
			rb = map.get(world);
		else
		{
			rb = new RenderBlocks(world);
			map.put(world, rb);
		}
		rb.renderBlockBrewingStand((BlockBrewingStand) Blocks.brewing_stand, tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
}
