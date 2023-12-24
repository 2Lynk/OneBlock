package lynk.oneblock.utils;

import lynk.oneblock.server.GameObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.io.File;
import java.util.Random;

import static lynk.oneblock.server.GameObject.configFileLocation;
import static lynk.oneblock.server.GameObject.configFileName;
import static lynk.oneblock.server.GameObject.respawnOneBlock;

public class Server {

    /**
     * Initializes the mod's server-side components when the Minecraft server starts. This method checks for the
     * existence of a save file and either reads from it or creates a new one if it doesn't exist. It then
     * respawns the 'One Block' in the overworld.
     *
     * @param server The instance of the Minecraft server.
     *
     * This method ensures that the game's state is properly initialized or restored at the beginning of a server
     * session, maintaining continuity in gameplay and mod functionality.
     */
    public static void start(MinecraftServer server) {
        //Check if save file exists, if not create one
        if(!doesSaveFileExist()){
            writeToSaveFile();
        }else{
            readSaveFile();
        };
        respawnOneBlock(server.getOverworld());
    }

    /**
     * Checks if the configuration save file exists.
     *
     * @return boolean True if the save file exists, false otherwise.
     *
     * This method is used to determine whether game state data needs to be read from or written to a file,
     * ensuring game settings and progress are maintained across server sessions.
     */
    private static boolean doesSaveFileExist() {
        File file = new File(configFileLocation + configFileName);
        return file.exists();
    }

    /**
     * Writes the current state of the game to a JSON file. This method is a wrapper around GameObject.saveToJson()
     * and is called when initializing the server if the save file does not exist.
     *
     * This ensures that a new game state is persisted from the start of the game, creating a point of reference
     * for future game sessions.
     */
    private static void writeToSaveFile() {
        GameObject.saveToJson();
    }

    /**
     * Reads the game's state from a JSON file. This method is a wrapper around GameObject.loadFromJson()
     * and is called during server initialization if a save file exists.
     *
     * It ensures that the game's state is restored from the last saved point, maintaining continuity in the
     * game's progress and settings across server restarts.
     */
    public static void readSaveFile() {
        GameObject.loadFromJson();
    }

    /**
     * Generates a random integer between 0 and a specified maximum value (exclusive).
     *
     * @param maxInt The upper bound for the random number generation (exclusive).
     * @return A random integer between 0 and maxInt.
     *
     * This utility method is used throughout the mod for generating random numbers within a defined range,
     * useful for various randomized game mechanics.
     */
    public static Integer randomInt(Integer maxInt){
        Random random = new Random();
        return random.nextInt(maxInt);
    }

    /**
     * Determines whether a rare item should spawn based on the current game state.
     *
     * @return Boolean True if a rare item should spawn, false otherwise.
     *
     * This method uses the current number of breaks and the configured chance of rare drops to decide
     * if a rare item should appear in the game, adding an element of randomness and rarity to the gameplay.
     */
    public static Boolean spawnRare(){
        if((GameObject.breaks % GameObject.breaksPerChanceOnRareDrop) == 0) {
            int randomNumber = rollTheDice(100);
            if(randomNumber <= GameObject.percentageRareDrop){
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a random integer within a specified range, from 0 to a given maximum value (inclusive).
     *
     * @param max The maximum value for the random number (inclusive).
     * @return A random integer between 0 and the specified maximum value.
     *
     * This method is used for generating random numbers for various probabilistic calculations within the mod.
     */
    public static int rollTheDice(Integer max) {
        int randomNumber = (int)(Math.random()*(max-0+1)+0);
        return randomNumber;
    }

    /**
     * Checks and updates the spawn point for a given player. If the player does not have a custom spawn point set,
     * it assigns a default spawn point. This method is particularly useful to ensure players always spawn in a valid,
     * expected location, especially after events that may alter spawn points or in custom world setups.
     *
     * @param player The ServerPlayerEntity instance representing the player whose spawn point is being checked and updated.
     */
    public static void checkAndNotifySpawnPoint(ServerPlayerEntity player) {
        BlockPos spawnPoint = player.getSpawnPointPosition();
        BlockPos defaultSpawnPoint = new BlockPos(GameObject.getOneBlockPos().getX(),GameObject.getOneBlockPos().getY()+1,GameObject.getOneBlockPos().getZ());
        if (spawnPoint == null) {
            // Logic if the player has a custom spawn point
            player.setSpawnPoint(World.OVERWORLD, defaultSpawnPoint, 0.0F, true, false);
        }
    }

    /**
     * Check if the block at a specific position is breakable.
     * @param block The world where the block exists.
     * @return true if breakable, false otherwise.
     */
    public static boolean isBlockBreakable(Block block) {
        // Check if the block is not air and has hardness not equal to -1.0f
        return !block.getName().getString().equals("AIR") && block.getHardness() != -1.0f;
    }

    /**
     * Checks if the specified block at the given position is a standard cube. A standard cube is defined as
     * having a full, cube-shaped collision box that fills the entire 1x1x1 block space. This method is useful
     * for determining if a block is a regular block like stone or dirt and not a special shape like a slab or stair.
     *
     * @param state The block state to check. This contains all the property data about the block.
     * @param world The world view where the block exists. This provides context about the block's surroundings.
     * @param pos The position of the block in the world. This is used to get the actual shape of the block in the world.
     * @return true if the block is a standard cube, false otherwise.
     */
    public static boolean isStandardCube(BlockState state, BlockView world, BlockPos pos) {
        // Retrieve the collision shape of the block
        VoxelShape shape = state.getCollisionShape(world, pos);

        // Check if the shape fills the entire 1x1x1 block space
        // This means checking if the bounding box of the shape is the full block
        return shape.getBoundingBox().equals(Block.createCuboidShape(0, 0, 0, 16, 16, 16));
    }

    /**
     * Handles interactions with blocks in the world, specifically preventing players from placing blocks
     * at certain restricted positions. This method is typically called when a player tries to interact with
     * the world, such as placing a block.
     *
     * @param playerEntity The player performing the interaction.
     * @param world The world in which the interaction occurs.
     * @param hand The hand being used by the player for the interaction.
     * @param blockHitResult The result of the block hit, containing the position and specifics of the interaction.
     * @return ActionResult indicating the result of the interaction. Returns FAIL if the player tries to place a block
     * at the restricted positions, otherwise PASS to allow normal interaction.
     */
    public static ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
        // Don't allow block placement at OneBlock position
        if (blockHitResult.getBlockPos().equals(GameObject.getOneBlockPos())) {
            playerEntity.sendMessage(Text.of("Not allowed to place blocks at the One Block position."), true);
            return ActionResult.FAIL;
        }

        BlockPos oneBlockAboveOneBlock = new BlockPos(GameObject.getOneBlockPos().getX(), GameObject.getOneBlockPos().getY()+1, GameObject.getOneBlockPos().getZ());
        BlockPos twoBlocksAboveOneBlock = new BlockPos(GameObject.getOneBlockPos().getX(), GameObject.getOneBlockPos().getY()+2, GameObject.getOneBlockPos().getZ());
        // Don't allow block placement at one or two blocks above OneBlock position
        if (blockHitResult.getBlockPos().equals(oneBlockAboveOneBlock) || blockHitResult.getBlockPos().equals(twoBlocksAboveOneBlock)) {
            playerEntity.sendMessage(Text.of("Not allowed to place blocks one or two blocks above the One Block."), true);
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }
}
