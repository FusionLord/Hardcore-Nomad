package net.firesquaredcore.helper;

public interface IProxy
{
	// Register Blocks
	public abstract void registerBlocks();

	// Register TileEntities
	public abstract void registerTileEntities();

	// Register Entites
	public abstract void registerEntities();

	// Register Events
	public abstract void registerEvents();

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

	// Register Dungeon Loot
	public abstract void registerDungeonLoot();

}
