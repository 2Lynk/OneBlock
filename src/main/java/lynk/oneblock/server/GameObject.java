package lynk.oneblock.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static lynk.oneblock.Oneblock.MOD_SAYS;
import static lynk.oneblock.utils.Server.*;

public class GameObject {
    // Your static fields
    public static int breaks = 0;
    public static int breaksThisLevel = 0;
    public static int currentLevel = 1;
    public static int levelMultiplier = 2;
    public static int OneBlockPosX = 0;
    public static int OneBlockPosY = 100;
    public static int OneBlockPosZ = 0;
    public static int breaksPerLevel = 100;
    public static int breaksNeededThisLevel = breaksPerLevel;
    public static int percentageRareDrop = 50;
    public static boolean rareDropInChest = false;
    public static int breaksPerChanceOnRareDrop = 50;
//    public static int breaksPerChanceSpawnTrader = 50;
    public static String[][] levels = {
            // Level 1: Basic Natural Blocks
            {"DIRT", "GRASS BLOCK", "PODZOL", "MYCELIUM"},
            // Level 2: Basic Wood Types
            {"OAK LOG", "BIRCH LOG", "SPRUCE LOG", "JUNGLE LOG", "ACACIA LOG", "DARK OAK LOG"},
            // Level 3: Stone Variants
            {"STONE", "COBBLESTONE", "ANDESITE", "GRANITE", "DIORITE", "MOSSY COBBLESTONE", "COAL ORE"},
            // Level 4: Farming Soil and Plants
            {"FARMLAND", "CLAY", "SUGAR CANE", "PUMPKIN", "MELON"},
            // Level 5: Nether Basics
            {"NETHER RACK", "GLOWSTONE", "QUARTZ ORE", "MAGMA BLOCK", "SOUL SOIL", "BASALT"},
            // Level 6: Leaves and More Wood Variants
            {"OAK LEAVES", "BIRCH LEAVES", "JUNGLE LEAVES", "ACACIA LEAVES", "DARK OAK LEAVES", "CRIMSON STEM", "WARPED STEM"},
            // Level 7: Ores and Minerals
            {"COAL ORE", "IRON ORE", "GOLD ORE", "LAPIS LAZULI ORE", "DIAMOND ORE", "EMERALD ORE", "REDSTONE ORE"},
            // Level 8: Decorative Blocks
            {"SANDSTONE", "CHISELED SANDSTONE", "CUT SANDSTONE", "PRISMARINE", "SEA LANTERN", "TERRACOTTA", "GLASS"},
            // Level 9: End Blocks
            {"END STONE", "PURPUR BLOCK", "END STONE BRICKS", "CHORUS PLANT", "PURPUR PILLAR"},
            // Level 10: Advanced Nether Blocks
            {"BLACKSTONE", "CRIMSON NYLIUM", "WARPED NYLIUM", "GILDED BLACKSTONE", "NETHER BRICK", "SHROOMLIGHT"},
            // Level 11: Ocean and Coral
            {"CORAL BLOCK", "CORAL", "CORAL FAN", "SPONGE", "WET SPONGE"},
            // Level 12: Ice and Snow
            {"ICE", "SNOW", "PACKED ICE", "BLUE ICE", "SNOW BLOCK"},
            // Level 13: Deepslate Variants
            {"DEEPSLATE", "COBBLED DEEPSLATE", "POLISHED DEEPSLATE", "DEEPSLATE COAL ORE", "DEEPSLATE IRON ORE", "DEEPSLATE COPPER ORE"},
            // Level 14: Building Blocks
            {"BRICKS", "STONE BRICKS", "CRACKED STONE BRICKS", "MOSSY STONE BRICKS", "CHISELED STONE BRICKS", "SMOOTH STONE"},
            // Level 15: Exotic Wood and Plants
            {"BAMBOO", "SUGAR CANE", "VINE", "LILY PAD", "DANDELION", "POPPY", "BLUE ORCHID"},
            // Level 16: Rare Earths and Stones
            {"TUFF", "CALCITE", "AMETHYST BLOCK", "COPPER ORE", "RAW COPPER BLOCK", "RAW IRON BLOCK"},
            // Level 17: A Touch of Green
            {"GRASS", "FERN", "DEAD BUSH", "SEA GRASS", "AZALEA", "FLOWERING AZALEA"},
            // Level 18: Varied Stone Types
            {"POLISHED GRANITE", "POLISHED DIORITE", "POLISHED ANDESITE", "POLISHED BASALT", "CHISELED QUARTZ BLOCK", "QUARTZ BRICKS"},
            // Level 19: Underwater Blocks
            {"PRISMARINE BRICKS", "DARK PRISMARINE", "TUBE CORAL BLOCK", "BRAIN CORAL BLOCK", "BUBBLE CORAL BLOCK", "FIRE CORAL BLOCK"},
            // Level 20: End Game Blocks
            {"CRYING OBSIDIAN", "END GATEWAY", "PURPUR PILLAR", "END ROD", "OBSIDIAN", "RESPAWN ANCHOR"},
            // Level 21: Lush Caves
            {"MOSS BLOCK", "GLOW BERRIES", "DRIP LEAF", "SPORADIC BLOSSOM", "CLAY BALL", "HANGING ROOTS", "MOSS CARPET"},
            // Level 22: Deep Dark Biome
            {"SCULK SENSOR", "SCULK GROWTH", "SCULK VEIN", "SCULK CATALYST", "SCULK SHRIEKER", "DEEPSLATE GOLD ORE", "DEEPSLATE REDSTONE ORE"},
            // Level 23: Mountain Peaks
            {"STONE", "GRAVEL", "IRON ORE", "COAL ORE", "COPPER ORE", "GLOW LICHEN", "POWDERED SNOW CAULDRON"},
            // Level 24: Underground Blocks
            {"TUFF", "CALCITE", "DEEPSLATE DIAMOND ORE", "DEEPSLATE EMERALD ORE", "DEEPSLATE LAPIS LAZULI ORE", "DEEPSLATE REDSTONE ORE"},
            // Level 25: Deepslate Ores and Cave Formations
            {"DEEPSLATE COPPER ORE", "POINTED DRIPSTONE", "DRIPSTONE BLOCK", "AMETHYST BUDS", "AMETHYST CLUSTER", "BUDDING AMETHYST"},
            // Level 26: Lush Underground and Rare Decoratives
            {"MOSSY COBBLESTONE", "MOSSY STONE BRICKS", "MUD", "HONEYCOMB BLOCK", "NETHER WART BLOCK", "WARPED WART BLOCK"},
            // Level 27: Nether Expansion 2
            {"SOUL SAND", "BASALT", "WEEPING VINES", "TWISTING VINES", "CRIMSON FUNGUS", "WARPED FUNGUS"},
            // Level 28: Jungle and Swamp
            {"JUNGLE LOG", "VINE", "LILY PAD", "MELON BLOCK", "COCOA BEANS", "LARGE FERN", "BAMBOO"},
            // Level 29: Taiga and Snowy Biomes
            {"SPRUCE LOG", "SNOW BLOCK", "ICE", "PACKED ICE", "BLUE ICE", "FROSTED ICE"},
            // Level 30: Overworld Nighttime
            {"GLOWSTONE", "JACK O'LANTERN", "LANTERN", "TORCH", "SEA LANTERN", "END ROD", "SHROOMLIGHT"},
            // Additional levels can be created to cover more specific or rare blocks...
    };
    public static String[] levelNames = {
            "Basic Natural Blocks",
            "Basic Wood Types",
            "Stone Variants",
            "Farming Soil and Plants",
            "Nether Basics",
            "Leaves and More Wood Variants",
            "Ores and Minerals",
            "Decorative Blocks",
            "End Blocks",
            "Advanced Nether Blocks",
            "Ocean and Coral",
            "Ice and Snow",
            "Deepslate Variants",
            "Building Blocks",
            "Exotic Wood and Plants",
            "Rare Earths and Stones",
            "A Touch of Green",
            "Varied Stone Types",
            "Underwater Blocks",
            "End Game Blocks",
            "Lush Caves",
            "Deep Dark Biome",
            "Mountain Peaks",
            "Underground Blocks",
            "Deepslate Ores and Cave Formations",
            "Lush Underground and Rare Decoratives",
            "Nether Expansion 2",
            "Jungle and Swamp",
            "Taiga and Snowy Biomes",
            "Overworld Nighttime"
            // Additional levels can be added...
    };
    public static String[][] rareDrops = {
        // Level 1: Basic Mob and Surface Drops
        {"STRING", "BONE", "ARROW", "ROTTEN FLESH", "FEATHER", "LEATHER", "EGG", "SOUL SAND", "SAND"},
        // Level 2: Farming and Simple Gathering
        {"WHEAT", "SEEDS", "APPLE", "CARROT", "POTATO", "SUGAR CANE", "PUMPKIN SEEDS", "MELON SEEDS"},
        // Level 3: Basic Underground Finds
        {"FLINT", "COAL", "IRON NUGGET", "GOLD NUGGET", "REDSTONE", "LAPIS LAZULI"},
        // Level 4: Nether Drops
        {"NETHER QUARTZ", "GLOWSTONE DUST", "BLAZE ROD", "NETHER WART", "GHAST TEAR", "MAGMA CREAM"},
        // Level 5: Fishing and Water Treasures
        {"RAW FISH", "RAW SALMON", "PUFFERFISH", "TROPICAL FISH", "PRISMARINE CRYSTALS", "PRISMARINE SHARD"},
        // Level 6: Advanced Mob Drops
        {"ENDER PEARL", "SLIME BALL", "SPIDER EYE", "GUNPOWDER", "PHANTOM MEMBRANE", "SHULKER SHELL"},
        // Level 7: Mining Riches
        {"DIAMOND", "EMERALD", "GOLD ORE", "IRON ORE", "COPPER ORE"},
        // Level 8: End Loot
        {"END CRYSTAL", "ENDER PEARL", "DRAGON'S BREATH", "ELYTRA", "SHULKER SHELL"},
        // Level 9: Rare Biome Finds
        {"BAMBOO", "COCOA BEANS", "CHORUS FRUIT", "PODZOL", "MYCELIUM", "BLUE ICE"},
        // Level 10: Specialty Items
        {"HONEYCOMB", "NETHERITE SCRAP", "ANCIENT DEBRIS", "AMETHYST SHARD", "GLOW BERRIES"},
        // Level 11: Overworld Night Drops
        {"SPIDER EYE", "ENDER PEARL", "BONE", "STRING", "GUNPOWDER", "ZOMBIE FLESH", "KELP", "SEA GRASS", "SEA PICKLE"},
        // Level 12: Farming Produce
        {"CARROT", "POTATO", "BEETROOT", "WHEAT", "MELON SLICE", "PUMPKIN"},
        // Level 13: Woodland Loot
        {"OAK SAPLING", "BIRCH SAPLING", "SPRUCE SAPLING", "JUNGLE SAPLING", "ACACIA SAPLING", "DARK OAK SAPLING"},
        // Level 14: Desert Treasures
        {"CACTUS", "DEAD BUSH", "RABBIT HIDE", "RABBIT FOOT", "PRISMARINE SHARD", "PRISMARINE CRYSTALS"},
        // Level 15: Arctic Finds
        {"SNOWBALL", "ICE", "PACKED ICE", "BLUE ICE", "POLAR BEAR FUR", "FROSTED ICE", "POWDER SNOW"},
        // Level 16: Underground Gems
        {"EMERALD", "DIAMOND", "GOLD ORE", "IRON ORE", "COAL", "REDSTONE"},
        // Level 17: Enchanted Items
        {"ENCHANTED BOOK", "GOLDEN APPLE", "ENCHANTED GOLDEN APPLE", "EXPERIENCE BOTTLE"},
        // Level 18: Deep Sea Loot
        {"SEA LANTERN", "SPONGE", "WET SPONGE", "HEART OF THE SEA", "NAUTILUS SHELL", "TROPICAL FISH"},
        // Level 19: Nether Fortresses
        {"NETHER WART", "BLAZE ROD", "WITHER SKELETON SKULL", "NETHER BRICK", "MAGMA CREAM", "GOLD SWORD"},
        // Level 20: The End Drops
        {"SHULKER SHELL", "ELYTRA", "DRAGON HEAD", "END STONE", "PURPUR BLOCK", "END ROD"},
        // Level 21: Rare Plants and Flowers
        {"LILY OF THE VALLEY", "SUNFLOWER", "LILAC", "ROSE BUSH", "PEONY", "BLUE ORCHID"},
        // Level 22: Exotic Nether Finds
        {"GLOWSTONE DUST", "QUARTZ", "NETHERITE INGOT", "BLACKSTONE", "GILDED BLACKSTONE", "CRYING OBSIDIAN"},
        // Level 23: Jungle Mysteries
        {"JUNGLE TEMPLE LOOT", "BAMBOO", "COCOA BEANS", "PARROT FEATHER", "OCELOT FUR", "MELON SEEDS"},
        // Level 24: Taiga Treasures
        {"SWEET BERRIES", "FOX FUR", "SPRUCE SAPLING", "RABBIT STEW", "WOLF FUR", "BEE NEST"},
        // Level 25: Mountain Riches
        {"EMERALD ORE", "SILVERFISH STONE", "GOAT HORN", "SNOW GOLEM HEAD", "MOUNTAIN MAP", "IRON PICKAXE"},
        // Level 26: Desert Pyramids
        {"GOLDEN APPLE", "DIAMOND HORSE ARMOR", "GOLD INGOT", "IRON INGOT", "BONE MEAL", "SADDLE"},
        // Level 27: Ocean Monuments
        {"PRISMARINE CRYSTALS", "PRISMARINE SHARD", "SPONGE", "SEA LANTERN", "WET SPONGE", "GOLD BLOCK"},
        // Level 28: Mushroom Island Delights
        {"MOOSHROOM FUR", "MUSHROOM", "RED MUSHROOM BLOCK", "BROWN MUSHROOM BLOCK", "MUSHROOM STEW", "MYCELIUM"},
        // Level 29: Villager Trades
        {"EMERALD", "BOOKSHELF", "CLOCK", "COMPASS", "MAP", "WRITTEN BOOK"},
        // Level 30: Ancient Ruins
        {"GOLDEN APPLE", "ENAMELLED SKULL", "CURSED ARMOR", "TREASURE MAP", "HEART OF THE SEA", "CONDUIT"}
    };

    public static String configFileLocation = "config/OneBlock/";
    public static String configFileName = "OneBlock.json";

    public static boolean randomBlockMode = false;
    public static boolean randomRareDropMode = false;
    public static boolean allowBlocksJustAboveOneBlock = false;


    // Nested class to mirror the static fields for serialization
    private static class GameObjectState {
        int breaks;
        int breaksThislevel;
        int currentLevel;
        int levelMultiplier;
        int oneBlockPosX;
        int oneBlockPosY;
        int oneBlockPosZ;
        int breaksPerLevel;
        int breaksNeededThisLevel;
        int percentageRareDrop;
        boolean rareDropInChest;
        int breaksPerChanceOnRareDrop;
//        int breaksPerChanceSpawnTrader;
        String[][] levels;

        String[] levelNames;
        String[][] rareDrops;
        boolean randomBlockMode;
        boolean randomRareDropMode;
        boolean allowBlocksJustAboveOneBlock;

        GameObjectState() {
            this.breaks = GameObject.breaks;
            this.breaksThislevel = GameObject.breaksThisLevel;
            this.currentLevel = GameObject.currentLevel;
            this.levelMultiplier = GameObject.levelMultiplier;
            this.oneBlockPosX = GameObject.OneBlockPosX;
            this.oneBlockPosY = GameObject.OneBlockPosY;
            this.oneBlockPosZ = GameObject.OneBlockPosZ;
            this.breaksPerLevel = GameObject.breaksPerLevel;
            this.percentageRareDrop = GameObject.percentageRareDrop;
            this.rareDropInChest = GameObject.rareDropInChest;
            this.breaksPerChanceOnRareDrop = GameObject.breaksPerChanceOnRareDrop;
//            this.breaksPerChanceSpawnTrader = GameObject.breaksPerChanceSpawnTrader;
            this.levels = GameObject.levels;
            this.levelNames = GameObject.levelNames;
            this.rareDrops = GameObject.rareDrops;
            this.randomBlockMode = GameObject.randomBlockMode;
            this.randomRareDropMode = GameObject.randomRareDropMode;
            this.allowBlocksJustAboveOneBlock = GameObject.allowBlocksJustAboveOneBlock;
        }
    }

    /**
     * Saves the current state of game settings to a JSON file. This method creates a new Gson instance for JSON
     * serialization, ensures the existence of the configuration directory, and then writes the current state of the
     * GameObject into a JSON file. The state is captured in a temporary GameObjectState instance, which is serialized
     * to JSON format. The JSON is then written to a file specified by combining configFileLocation and configFileName.
     *
     * If the configuration directory does not exist, it attempts to create it. Similarly, if the file does not exist,
     * it attempts to create a new file. Messages indicating the status of directory and file creation are printed to the
     * console. After successful file creation or if the file already exists, the method writes the serialized JSON to the
     * file. In case of any I/O errors during this process, an error message is printed and the stack trace is printed.
     *
     * Note: This method utilizes the Gson library for JSON serialization.
     *
     * @throws IOException if there is an error in creating the file, writing to the file, or any other I/O operation.
     *
     * This method is crucial for persisting game settings, allowing the game's state to be saved and reloaded across
     * sessions. This functionality is particularly important in custom Minecraft mods or server implementations where
     * game settings need to be dynamically managed and persisted.
     */
    public static void saveToJson() {
        // Create a Gson instance
        Gson gson = new Gson();
        File directory = new File(configFileLocation);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (dirCreated) {
                System.out.println(MOD_SAYS + "Config directory created successfully");
            } else {
                System.out.println(MOD_SAYS + "Failed to create config directory");
                return;
            }
        }

        gson = new Gson();
        GameObjectState state = new GameObjectState(); // Create a temporary instance
        String json = gson.toJson(state); // Serialize the temporary instance
        File file = new File(configFileLocation + configFileName);
        try {
            if (file.createNewFile()) {
                System.out.println(MOD_SAYS + "File created: " + file.getName());
            } else {
                System.out.println("MOD_SAYS + File already exists.");
            }

            // Writing content to the file
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println(MOD_SAYS + "An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Loads configuration settings from a JSON file and sets corresponding fields in the GameObject class.
     * This method reads a JSON file specified by the combined path from configFileLocation and configFileName.
     * If the file exists, it parses the file and updates the GameObject class fields based on the JSON content.
     * The fields include game settings such as breaks, level details, positions, rare drop chances, and more.
     * In case of a missing file or a parsing error, an appropriate error message is printed or the stack trace is printed.
     *
     * Note: This method utilizes the Gson library for JSON parsing.
     *
     * The method checks for the existence of each expected field in the JSON object and only updates the corresponding
     * static field in GameObject if the JSON field is present. This ensures that the game's configuration can be flexibly
     * defined in the JSON file.
     *
     * Fields loaded include:
     * - General game settings like breaks, current level, level multiplier.
     * - Positional data for the 'One Block'.
     * - Level progression and rare drop settings.
     *
     * @throws FileNotFoundException if the configuration file is not found.
     * @throws JsonSyntaxException if there is an error in parsing the JSON file.
     *
     * This method is essential for initializing or updating game settings dynamically, allowing for a flexible
     * and configurable mod or game environment in Minecraft.
     */
    public static void loadFromJson() {
        String filePath = configFileLocation + configFileName;
        // Check if the file exists
        if (!Files.exists(Paths.get(filePath))) {
            System.err.println("File not found: " + filePath);
            return;
        }

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Set static fields individually, checking for existence
            if (jsonObject.has("breaks")) {
                GameObject.breaks = gson.fromJson(jsonObject.get("breaks"), Integer.TYPE);
            }
            if (jsonObject.has("breaksThisLevel")) {
                GameObject.breaksThisLevel = gson.fromJson(jsonObject.get("breaksThisLevel"), Integer.TYPE);
            }
            if (jsonObject.has("currentLevel")) {
                GameObject.currentLevel = gson.fromJson(jsonObject.get("currentLevel"), Integer.TYPE);
            }
            if (jsonObject.has("levelMultiplier")) {
                GameObject.levelMultiplier = gson.fromJson(jsonObject.get("levelMultiplier"), Integer.TYPE);
            }
            if (jsonObject.has("OneBlockPosX")) {
                GameObject.OneBlockPosX = gson.fromJson(jsonObject.get("OneBlockPosX"), Integer.TYPE);
            }
            if (jsonObject.has("OneBlockPosY")) {
                GameObject.OneBlockPosY = gson.fromJson(jsonObject.get("OneBlockPosY"), Integer.TYPE);
            }
            if (jsonObject.has("OneBlockPosZ")) {
                GameObject.OneBlockPosZ = gson.fromJson(jsonObject.get("OneBlockPosZ"), Integer.TYPE);
            }
            if (jsonObject.has("breaksPerLevel")) {
                GameObject.breaksPerLevel = gson.fromJson(jsonObject.get("breaksPerLevel"), Integer.TYPE);
                GameObject.breaksNeededThisLevel = gson.fromJson(jsonObject.get("breaksPerLevel"), Integer.TYPE);
                for (Integer i = 1; i < GameObject.currentLevel; i++) {
                    GameObject.breaksNeededThisLevel = GameObject.breaksNeededThisLevel * levelMultiplier;
                }
            }
            if (jsonObject.has("percentageRareDrop")) {
                GameObject.percentageRareDrop = gson.fromJson(jsonObject.get("percentageRareDrop"), Integer.TYPE);
            }
            if (jsonObject.has("rareDropInChest")) {
                GameObject.rareDropInChest = gson.fromJson(jsonObject.get("rareDropInChest"), Boolean.TYPE);
            }
            if (jsonObject.has("breaksPerChanceOnRareDrop")) {
                GameObject.breaksPerChanceOnRareDrop = gson.fromJson(jsonObject.get("breaksPerChanceOnRareDrop"), Integer.TYPE);
            }
//            if (jsonObject.has("breaksPerChanceSpawnTrader")) {
//                GameObject.breaksPerChanceSpawnTrader = gson.fromJson(jsonObject.get("breaksPerChanceSpawnTrader"), Integer.TYPE);
//            }
            if (jsonObject.has("levels")) {
                GameObject.levels = gson.fromJson(jsonObject.get("levels"), String[][].class);
            }
            if (jsonObject.has("rareDrops")) {
                GameObject.rareDrops = gson.fromJson(jsonObject.get("rareDrops"), String[][].class);
            }
            if (jsonObject.has("randomBlockMode")) {
                GameObject.randomBlockMode = gson.fromJson(jsonObject.get("randomBlockMode"), Boolean.TYPE);
            }
            if (jsonObject.has("randomRareDropMode")) {
                GameObject.randomRareDropMode = gson.fromJson(jsonObject.get("randomRareDropMode"), Boolean.TYPE);
            }
            if (jsonObject.has("allowBlocksJustAboveOneBlock")) {
                GameObject.allowBlocksJustAboveOneBlock = gson.fromJson(jsonObject.get("allowBlocksJustAboveOneBlock"), Boolean.TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and returns the position of the 'One Block' as a BlockPos object. This method constructs a new BlockPos
     * instance using coordinates specified in the GameObject class. The 'One Block' is a specific point in the game world,
     * typically central to gameplay mechanics in a Minecraft mod or custom environment.
     *
     * Note: This method relies on static fields from the GameObject class for the position coordinates:
     * - GameObject.OneBlockPosX: The x-coordinate of the 'One Block'.
     * - GameObject.OneBlockPosY: The y-coordinate of the 'One Block'.
     * - GameObject.OneBlockPosZ: The z-coordinate of the 'One Block'.
     *
     * @return A BlockPos object representing the position of the 'One Block'.
     *
     * The BlockPos object returned by this method can be used in various game mechanics, such as spawning entities,
     * placing blocks, or determining player proximity to the 'One Block' in custom Minecraft mods or server implementations.
     */
    public static BlockPos getOneBlockPos(){
        BlockPos oneBlockPos = new BlockPos(GameObject.OneBlockPosX, GameObject.OneBlockPosY, GameObject.OneBlockPosZ);
        return oneBlockPos;
    }

    /**
     * Increments the count of total blocks broken and blocks broken in the current level. This method
     * is used to track player progress by incrementing two counters: one for the total number of blocks
     * broken during the game, and another for the number of blocks broken in the current level.
     *
     * Note: This method modifies static fields in the GameObject class:
     * - GameObject.breaks: An integer representing the total number of blocks broken across all levels.
     * - GameObject.breaksThisLevel: An integer representing the number of blocks broken in the current level.
     *
     * These counters can be used for various purposes, such as determining player progress, triggering level
     * advancements, or other game mechanics in a Minecraft mod or custom server environment.
     */
    public static void incrementOneBlockBroken(){
        GameObject.breaks++;
        GameObject.breaksThisLevel++;
    }

    /**
     * Determines and updates the current level based on the number of breaks achieved. This method increments
     * the level of the game when the number of breaks for the current level meets the required threshold. Upon
     * leveling up, it resets the count of breaks for the new level, updates the breaks needed for the next level,
     * and broadcasts level-up messages to all players on the server. Additionally, if a name is assigned to the new
     * level, a message indicating the level theme is also broadcast.
     *
     * @param server The Minecraft server instance used for broadcasting messages to players. This should be an
     *               instance of MinecraftServer.
     *
     * Note: This method relies on several static fields from the GameObject class:
     * - GameObject.breaksThisLevel: The current number of breaks achieved.
     * - GameObject.breaksNeededThisLevel: The number of breaks required to advance to the next level.
     * - GameObject.currentLevel: The current level of the game.
     * - GameObject.levels: An array representing the total number of levels available.
     * - GameObject.levelMultiplier: A multiplier used to calculate the breaks needed for subsequent levels.
     * - GameObject.levelNames: An array containing names for each level, if available.
     * - MOD_SAYS: A string prefix used for broadcasting messages.
     *
     * This method is designed for use in game scenarios where player progression is measured in terms of 'breaks',
     * such as in a mining or resource-gathering context within a Minecraft mod or custom server implementation.
     */
    public static void determineLevel(MinecraftServer server){
        if(GameObject.breaksThisLevel == GameObject.breaksNeededThisLevel && GameObject.currentLevel < GameObject.levels.length){
            GameObject.currentLevel++;
            GameObject.breaksThisLevel = 0;
            GameObject.breaksNeededThisLevel = GameObject.breaksNeededThisLevel * GameObject.levelMultiplier;
            String message = (MOD_SAYS + "OneBlock leveled up! Now level: " + GameObject.currentLevel);
            server.getPlayerManager().broadcast(Text.of(message), false);
            if(GameObject.levelNames[GameObject.currentLevel-1] != null){
                message = (MOD_SAYS + "Level theme: " + GameObject.levelNames[GameObject.currentLevel-1]);
                server.getPlayerManager().broadcast(Text.of(message), false);
            }
        }
    }

    /**
     * Selects a rare item from a predefined list based on the current level in the game and returns it as an ItemStack.
     * This method randomly selects an item from the rareDrops array corresponding to the current level. If the selected
     * item is found in the item registry, it is returned as an ItemStack. Otherwise, a default ItemStack of dirt is
     * returned, indicating an error in the selection process. Additionally, if the rare item cannot be found, a message
     * is broadcast to all players on the server indicating the failure to find the specified item.
     *
     * @param world The world context, used to access the server for broadcasting messages. This should be an instance
     *              of World associated with the Minecraft server.
     *
     * Note: This method relies on several external methods, classes, and arrays:
     * - GameObject.currentLevel: An integer representing the current level in the game.
     * - GameObject.rareDrops: A 2D array containing lists of rare item names for each level.
     * - randomInt(int max): A method to generate a random integer within a specified range.
     * - Registries.ITEM: A registry of all available item types.
     * - MOD_SAYS: A string prefix for broadcasting messages.
     *
     * @return An ItemStack representing the selected rare item. Returns an ItemStack of dirt if the item is not found.
     *
     * This method is particularly useful in game scenarios where special items are rewarded based on progression,
     * such as in custom Minecraft mods or server implementations.
     */
    public static ItemStack selectRare(World world){
        ItemStack stack = new ItemStack(Items.DIRT); // If the dirt spawns, it's an error
        if(GameObject.randomRareDropMode){
            // Create an instance of Random class
            Random random = new Random();
            // Generate a random index between 0 (inclusive) and the size of the registry (exclusive)
            int randomIndex = random.nextInt(Registries.ITEM.size());

            // Select a random item from the registry using the random index
            Item randomItem = Registries.ITEM.get(randomIndex);
            stack = new ItemStack(randomItem);
        }else{
            Boolean itemFound = false;
            MinecraftServer server = world.getServer();
            Integer randomLevel = randomInt(GameObject.currentLevel);
            Integer maxRareLevel = GameObject.rareDrops.length;
            Integer randomRare = null;
            if(randomLevel < maxRareLevel){
                randomRare = randomInt(rareDrops[randomLevel].length);// get a random block from the array
            }else{
                randomLevel = randomInt(maxRareLevel);
                randomRare = randomInt(rareDrops[maxRareLevel-1].length);// get a random block from the array
            }

            for(Item item : Registries.ITEM){
                if(item.getName().getString().toUpperCase().equals(GameObject.rareDrops[randomLevel][randomRare].toUpperCase())){
                    stack = new ItemStack(item);
                    itemFound = true;
                }
            }
            if(!itemFound){
                String message = (MOD_SAYS + "Could not find the following item: \n " + GameObject.rareDrops[randomLevel][randomRare].toString());
                server.getPlayerManager().broadcast(Text.of(message), false);
            }
        }

        return stack;
    }

    /**
     * Spawns a chest at a specific position in the given world and populates it with a rare item.
     * This method sets a chest block at the position returned by GameObject.getOneBlockPos()
     * and then attempts to cast the block entity at that position to a ChestBlockEntity. If successful,
     * it places a rare item, obtained from the selectRare method, into the first slot of the chest.
     *
     * @param world The world where the chest will be spawned and populated. This should be an instance
     *              of World associated with the Minecraft server.
     *
     * Note: This method relies on several external methods and classes:
     * - GameObject.getOneBlockPos(): A method to determine the position for spawning the chest.
     * - Blocks.CHEST: A reference to the chest block type.
     * - selectRare(World world): A method to select a rare item based on the provided world context.
     * - ChestBlockEntity: The block entity class for a chest, used to manipulate the chest's inventory.
     *
     * After placing the rare item in the chest, the method optionally notifies the world of a block update at
     * the chest's position. This is useful for ensuring other game mechanics react appropriately to the new chest.
     *
     * This method is typically used in mods or custom implementations for Minecraft servers to add exciting
     * treasure-finding elements to the game.
     */
    public static void spawnChestWithRare(World world){
        world.setBlockState(GameObject.getOneBlockPos(), Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(GameObject.getOneBlockPos()) instanceof ChestBlockEntity chestBlockEntity) {
            // Insert the ItemStack into the first slot of the chest
            chestBlockEntity.setStack(0, selectRare(world));

            // Notify the world of block update (optional, but can be useful)
            world.updateListeners((GameObject.getOneBlockPos()), Blocks.CHEST.getDefaultState(), Blocks.CHEST.getDefaultState(), 3);
        }
    }

    /**
     * Spawns a rare item in the given world at a specific position. This method creates a new item entity
     * with a rare item, determined by the selectRare method, and spawns it slightly above the position
     * returned by the GameObject.getOneBlockPos() method.
     *
     * @param world The world where the rare item will be spawned. This should be an instance of World
     *              associated with the Minecraft server.
     *
     * Note: This method relies on several external methods and classes:
     * - GameObject.getOneBlockPos(): A method to determine the position for spawning the rare item.
     * - selectRare(World world): A method to select a rare item based on the provided world context.
     * - ItemEntity: The class used to create a new item entity for spawning the rare item.
     *
     * The rare item is spawned one block above the position returned by getOneBlockPos(), to ensure
     * it does not spawn inside a block.
     *
     * This method is typically used in a mod or custom implementation for a Minecraft server to add
     * an element of rarity or special rewards in the game.
     */
    public static void spawnRareItem(World world){
        world.spawnEntity(new ItemEntity(world, GameObject.getOneBlockPos().getX(), GameObject.getOneBlockPos().getY()+1, GameObject.getOneBlockPos().getZ(), selectRare(world)));
    }

    /**
     * Respawns a single block in the given world. This method selects a random block type based on the current level
     * and attempts to respawn it at a specific position determined by the getOneBlockPos method. If the block type
     * cannot be found in the block registry, a grass block is placed instead, and a message is broadcast to all players
     * indicating the failure to find the specified block.
     *
     * @param world The world in which the block will be respawned. This should be an instance of World associated with
     *              the Minecraft server.
     *
     * Note: This method requires several external dependencies:
     * - MinecraftServer: To access the server instance from the world.
     * - GameObject.currentLevel: To determine the current level for random block selection.
     * - Registries.BLOCK: A registry of all available block types.
     * - getOneBlockPos(): A method to determine the position where the block will be respawned.
     * - Blocks.GRASS_BLOCK: A default block type used as a fallback.
     * - MOD_SAYS: A string prefix for broadcasting messages.
     *
     * It's assumed that this method is part of a mod or custom implementation in a Minecraft server environment.
     */
    public static void respawnOneBlock(World world){
        MinecraftServer server = world.getServer();
        if(GameObject.randomBlockMode){
            // Create an instance of Random class
            Random random = new Random();
            // Generate a random index between 0 (inclusive) and the size of the registry (exclusive)
            int randomIndex = random.nextInt(Registries.BLOCK.size());

            // Select a random item from the registry using the random index
            Block randomBlock = Registries.BLOCK.get(randomIndex);

            while (true) {
                if (isBlockBreakable(randomBlock) && isStandardCube(randomBlock.getDefaultState(), world, GameObject.getOneBlockPos())) {
                    break;
                }
                randomBlock = Registries.BLOCK.get(randomIndex);
            }

            world.setBlockState(getOneBlockPos(), randomBlock.getDefaultState());

        }else{
            Integer randomLevel = randomInt(GameObject.currentLevel);
            Integer randomBlock = randomInt(levels[randomLevel].length);
            Boolean blockFound = false;
            for(Block block : Registries.BLOCK) {
                if (block.getName().getString().toUpperCase().equals(levels[randomLevel][randomBlock].toString().toUpperCase())) {
                    world.setBlockState(getOneBlockPos(), block.getDefaultState());
                    blockFound = true;
                }
            }
            if(!blockFound){
                world.setBlockState(getOneBlockPos(), Blocks.GRASS_BLOCK.getDefaultState());
                String message = (MOD_SAYS + "Could not find the following block: \n " + levels[randomLevel][randomBlock].toString());
                server.getPlayerManager().broadcast(Text.of(message), false);
            }
        }
    }

    /**
     * Calculates and returns the number of remaining breaks needed to reach the next level in the game.
     * This method computes the difference between the total breaks needed for the current level and
     * the number of breaks achieved so far. The result represents how many more breaks are required
     * to advance to the next level.
     *
     * Note: This method relies on static fields from the GameObject class:
     * - GameObject.breaksNeededThisLevel: An integer representing the total number of breaks needed
     *   to complete the current level.
     * - GameObject.breaksThisLevel: An integer representing the number of breaks achieved so far in
     *   the current level.
     *
     * @return An integer representing the number of additional breaks required to reach the next level.
     *         This will be a non-negative number, where a zero or negative value indicates that the
     *         requirement for the current level has been met or exceeded.
     *
     * This method is useful in game scenarios where progress is measured in terms of 'breaks', such as
     * in a mining or resource gathering context. It can be used to update players on their progress or
     * trigger level advancement.
     */
    public static int calculateRemainingBreaks() {
        // Calculate the remaining breaks needed for the next level
        return GameObject.breaksNeededThisLevel - GameObject.breaksThisLevel;
    }
}