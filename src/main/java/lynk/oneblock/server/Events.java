package lynk.oneblock.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import static lynk.oneblock.server.GameObject.*;
import static lynk.oneblock.utils.Server.spawnRare;

public class Events {

    /**
     * Handles the game logic when a block is broken in the One Block scenario. This method performs a series of operations
     * whenever a block is broken, including incrementing the block break counters, determining if the level should be
     * updated, and handling the spawning of rare items. The sequence of actions is as follows:
     *
     * 1. Increment the total and level-specific break counters.
     * 2. Check and update the game level if necessary.
     * 3. Determine whether a rare item should spawn. If so, decide between spawning the item directly or in a chest.
     * 4. Respawn a block in the world, unless a chest with a rare item is spawned.
     *
     * @param world The world in which the block break event occurs. This should be an instance of World associated with
     *              the Minecraft server.
     *
     * Note: This method uses several other methods for its operations:
     * - incrementOneBlockBroken(): Increments the counters for blocks broken.
     * - determineLevel(MinecraftServer server): Updates the game level based on the number of breaks.
     * - spawnRare(): Checks if a rare item should spawn.
     * - spawnChestWithRare(World world): Spawns a chest with a rare item in the world.
     * - spawnRareItem(World world): Spawns a rare item directly in the world.
     * - respawnOneBlock(World world): Respawns a block in the world.
     *
     * The method is designed to encapsulate the core game mechanics related to block breaking in a custom Minecraft mod
     * or server implementation. It ensures a cohesive and controlled flow of gameplay events following a block break.
     */
    public static void handleOneBlockBreak(World world) {
        MinecraftServer server = world.getServer();
        incrementOneBlockBroken();
        determineLevel(server);
        if (spawnRare()) {
            if (rareDropInChest) {
                spawnChestWithRare(world);
            } else {
                spawnRareItem(world);
                respawnOneBlock(world);
            }
        } else {
            respawnOneBlock(world);
        }
    }
}