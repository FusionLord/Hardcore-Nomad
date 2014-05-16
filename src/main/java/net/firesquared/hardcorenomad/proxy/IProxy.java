package net.firesquared.hardcorenomad.proxy;

public interface IProxy
{
	// Register Blocks
	public abstract void registerBlocks();

	// Register TileEntities
	public abstract void registerTileEntities();

	// Register Items
	public abstract void registerItems();

	// Register WorldEvents
	public abstract void registerWorldEvents();

	// Register PlayerEvents
	public abstract void registerPlayerEvents();

	// Register Packets
	public abstract void initPacketHandler();

	public abstract void postInitPacketHandler();

	// Register Crafting Recipes
	public abstract void registerRecipes();
}
