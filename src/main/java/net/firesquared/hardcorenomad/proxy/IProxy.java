package net.firesquared.HardcoreNomad.proxy;

public interface IProxy {
    // Register Blocks
    public abstract void registerBlocks();

    // Register TileEntities
    public abstract void registerTileEntities();

    // Register Items
    public abstract void registerItems();
}
