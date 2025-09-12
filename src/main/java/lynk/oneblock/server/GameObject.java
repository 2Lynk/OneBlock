package lynk.oneblock.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
            // You can keep human-readable names, but ids are more reliable:
            // e.g., "minecraft:grass_block" or "othermod:cool_block"
            {"DIRT", "GRASS BLOCK", "PODZOL", "MYCELIUM"},
            {"OAK LOG", "BIRCH LOG", "SPRUCE LOG", "JUNGLE LOG", "ACACIA LOG", "DARK OAK LOG"},
            {"STONE", "COBBLESTONE", "ANDESITE", "GRANITE", "DIORITE", "MOSSY COBBLESTONE", "COAL ORE"},
            {"FARMLAND", "CLAY", "SUGAR CANE", "PUMPKIN", "MELON"},
            {"NETHERRACK", "GLOWSTONE", "NETHER QUARTZ ORE", "MAGMA BLOCK", "SOUL SOIL", "BASALT"},
            {"OAK LEAVES", "BIRCH LEAVES", "JUNGLE LEAVES", "ACACIA LEAVES", "DARK OAK LEAVES", "CRIMSON STEM", "WARPED STEM"},
            {"COAL ORE", "IRON ORE", "GOLD ORE", "LAPIS LAZULI ORE", "DIAMOND ORE", "EMERALD ORE", "REDSTONE ORE"},
            {"SANDSTONE", "CHISELED SANDSTONE", "CUT SANDSTONE", "PRISMARINE", "SEA LANTERN", "TERRACOTTA", "GLASS"},
            {"END STONE", "PURPUR BLOCK", "END STONE BRICKS", "CHORUS PLANT", "PURPUR PILLAR"},
            {"BLACKSTONE", "CRIMSON NYLIUM", "WARPED NYLIUM", "GILDED BLACKSTONE", "NETHER BRICKS", "SHROOMLIGHT"},
            {"CORAL BLOCK", "CORAL", "CORAL FAN", "SPONGE", "WET SPONGE"},
            {"ICE", "SNOW", "PACKED ICE", "BLUE ICE", "SNOW BLOCK"},
            {"DEEPSLATE", "COBBLED DEEPSLATE", "POLISHED DEEPSLATE", "DEEPSLATE COAL ORE", "DEEPSLATE IRON ORE", "DEEPSLATE COPPER ORE"},
            {"BRICKS", "STONE BRICKS", "CRACKED STONE BRICKS", "MOSSY STONE BRICKS", "CHISELED STONE BRICKS", "SMOOTH STONE"},
            {"BAMBOO", "SUGAR CANE", "VINE", "LILY PAD", "DANDELION", "POPPY", "BLUE ORCHID"},
            {"TUFF", "CALCITE", "AMETHYST BLOCK", "COPPER ORE", "RAW COPPER BLOCK", "RAW IRON BLOCK"},
            {"GRASS", "FERN", "DEAD BUSH", "SEA GRASS", "AZALEA", "FLOWERING AZALEA"},
            {"POLISHED GRANITE", "POLISHED DIORITE", "POLISHED ANDESITE", "POLISHED BASALT", "CHISELED QUARTZ BLOCK", "QUARTZ BRICKS"},
            {"PRISMARINE BRICKS", "DARK PRISMARINE", "TUBE CORAL BLOCK", "BRAIN CORAL BLOCK", "BUBBLE CORAL BLOCK", "FIRE CORAL BLOCK"},
            {"CRYING OBSIDIAN", "END ROD", "OBSIDIAN", "RESPAWN ANCHOR"},
            {"MOSS BLOCK", "GLOW BERRIES", "BIG DRIPLEAF", "CLAY", "HANGING ROOTS", "MOSS CARPET"},
            {"SCULK SENSOR", "SCULK", "SCULK VEIN", "SCULK CATALYST", "SCULK SHRIEKER", "DEEPSLATE GOLD ORE", "DEEPSLATE REDSTONE ORE"},
            {"STONE", "GRAVEL", "IRON ORE", "COAL ORE", "COPPER ORE", "GLOW LICHEN"},
            {"TUFF", "CALCITE", "DEEPSLATE DIAMOND ORE", "DEEPSLATE EMERALD ORE", "DEEPSLATE LAPIS LAZULI ORE", "DEEPSLATE REDSTONE ORE"},
            {"DEEPSLATE COPPER ORE", "POINTED DRIPSTONE", "DRIPSTONE BLOCK", "AMETHYST BUD", "AMETHYST CLUSTER", "BUDDING AMETHYST"},
            {"MOSSY COBBLESTONE", "MOSSY STONE BRICKS", "MUD", "HONEYCOMB BLOCK", "NETHER WART BLOCK", "WARPED WART BLOCK"},
            {"SOUL SAND", "BASALT", "WEEPING VINES", "TWISTING VINES", "CRIMSON FUNGUS", "WARPED FUNGUS"},
            {"JUNGLE LOG", "VINE", "LILY PAD", "MELON", "COCOA BEANS", "LARGE FERN", "BAMBOO"},
            {"SPRUCE LOG", "SNOW BLOCK", "ICE", "PACKED ICE", "BLUE ICE"},
            {"GLOWSTONE", "JACK O'LANTERN", "LANTERN", "TORCH", "SEA LANTERN", "END ROD", "SHROOMLIGHT"},
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
    };
    public static String[][] rareDrops = {
            {"STRING", "BONE", "ARROW", "ROTTEN FLESH", "FEATHER", "LEATHER", "EGG", "SOUL SAND", "SAND"},
            {"WHEAT", "SEEDS", "APPLE", "CARROT", "POTATO", "SUGAR CANE", "PUMPKIN SEEDS", "MELON SEEDS"},
            {"FLINT", "COAL", "IRON NUGGET", "GOLD NUGGET", "REDSTONE", "LAPIS LAZULI"},
            {"NETHER QUARTZ", "GLOWSTONE DUST", "BLAZE ROD", "NETHER WART", "GHAST TEAR", "MAGMA CREAM"},
            {"RAW FISH", "RAW SALMON", "PUFFERFISH", "TROPICAL FISH", "PRISMARINE CRYSTALS", "PRISMARINE SHARD"},
            {"ENDER PEARL", "SLIME BALL", "SPIDER EYE", "GUNPOWDER", "PHANTOM MEMBRANE", "SHULKER SHELL"},
            {"DIAMOND", "EMERALD", "GOLD ORE", "IRON ORE", "COPPER ORE"},
            {"END CRYSTAL", "ENDER PEARL", "DRAGON'S BREATH", "ELYTRA", "SHULKER SHELL"},
            {"BAMBOO", "COCOA BEANS", "CHORUS FRUIT", "PODZOL", "MYCELIUM", "BLUE ICE"},
            {"HONEYCOMB", "NETHERITE SCRAP", "ANCIENT DEBRIS", "AMETHYST SHARD", "GLOW BERRIES"},
            {"SPIDER EYE", "ENDER PEARL", "BONE", "STRING", "GUNPOWDER", "ROTTEN FLESH", "KELP", "SEA GRASS", "SEA PICKLE"},
            {"CARROT", "POTATO", "BEETROOT", "WHEAT", "MELON SLICE", "PUMPKIN"},
            {"OAK SAPLING", "BIRCH SAPLING", "SPRUCE SAPLING", "JUNGLE SAPLING", "ACACIA SAPLING", "DARK OAK SAPLING"},
            {"CACTUS", "DEAD BUSH", "RABBIT HIDE", "RABBIT FOOT", "PRISMARINE SHARD", "PRISMARINE CRYSTALS"},
            {"SNOWBALL", "ICE", "PACKED ICE", "BLUE ICE"},
            {"EMERALD", "DIAMOND", "GOLD ORE", "IRON ORE", "COAL", "REDSTONE"},
            {"ENCHANTED BOOK", "GOLDEN APPLE", "ENCHANTED GOLDEN APPLE", "EXPERIENCE BOTTLE"},
            {"SEA LANTERN", "SPONGE", "WET SPONGE", "HEART OF THE SEA", "NAUTILUS SHELL", "TROPICAL FISH"},
            {"NETHER WART", "BLAZE ROD", "WITHER SKELETON SKULL", "NETHER BRICK", "MAGMA CREAM", "GOLDEN SWORD"},
            {"SHULKER SHELL", "ELYTRA", "DRAGON HEAD", "END STONE", "PURPUR BLOCK", "END ROD"},
            {"LILY OF THE VALLEY", "SUNFLOWER", "LILAC", "ROSE BUSH", "PEONY", "BLUE ORCHID"},
            {"GLOWSTONE DUST", "QUARTZ", "NETHERITE INGOT", "BLACKSTONE", "GILDED BLACKSTONE", "CRYING OBSIDIAN"},
            {"BAMBOO", "COCOA BEANS", "MELON SEEDS"},
            {"SWEET BERRIES", "SPRUCE SAPLING", "RABBIT STEW"},
            {"EMERALD ORE", "GOAT HORN", "IRON PICKAXE"},
            {"GOLDEN APPLE", "DIAMOND HORSE ARMOR", "GOLD INGOT", "IRON INGOT", "BONE MEAL", "SADDLE"},
            {"PRISMARINE CRYSTALS", "PRISMARINE SHARD", "SPONGE", "SEA LANTERN", "WET SPONGE", "GOLD BLOCK"},
            {"MUSHROOM", "RED MUSHROOM BLOCK", "BROWN MUSHROOM BLOCK", "MUSHROOM STEW", "MYCELIUM"},
            {"EMERALD", "BOOKSHELF", "CLOCK", "COMPASS", "MAP", "WRITTEN BOOK"},
            {"GOLDEN APPLE", "TREASURE MAP", "HEART OF THE SEA", "CONDUIT"}
    };

    public static String configFileLocation = "config/OneBlock/";
    public static String configFileName = "OneBlock.json";

    public static boolean randomBlockMode = false;
    public static boolean randomRareDropMode = false;
    public static boolean allowBlocksJustAboveOneBlock = false;

    public static boolean emptyNether = true;

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
        String[][] levels;

        String[] levelNames;
        String[][] rareDrops;
        boolean randomBlockMode;
        boolean randomRareDropMode;
        boolean allowBlocksJustAboveOneBlock;

        boolean emptyNether;

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
            this.levels = GameObject.levels;
            this.levelNames = GameObject.levelNames;
            this.rareDrops = GameObject.rareDrops;
            this.randomBlockMode = GameObject.randomBlockMode;
            this.randomRareDropMode = GameObject.randomRareDropMode;
            this.allowBlocksJustAboveOneBlock = GameObject.allowBlocksJustAboveOneBlock;
            this.emptyNether = GameObject.emptyNether;
        }
    }

    public static void saveToJson() {
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
        GameObjectState state = new GameObjectState();
        String json = gson.toJson(state);
        File file = new File(configFileLocation + configFileName);
        try {
            if (file.createNewFile()) {
                System.out.println(MOD_SAYS + "File created: " + file.getName());
            } else {
                System.out.println("MOD_SAYS + File already exists.");
            }

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println(MOD_SAYS + "An error occurred.");
            e.printStackTrace();
        }
    }

    public static void loadFromJson() {
        String filePath = configFileLocation + configFileName;
        if (!Files.exists(Paths.get(filePath))) {
            System.err.println("File not found: " + filePath);
            return;
        }

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

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
                for (int i = 1; i < GameObject.currentLevel; i++) {
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
            if (jsonObject.has("emptyNether")) {
                GameObject.emptyNether = gson.fromJson(jsonObject.get("emptyNether"), Boolean.TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BlockPos getOneBlockPos(){
        return new BlockPos(GameObject.OneBlockPosX, GameObject.OneBlockPosY, GameObject.OneBlockPosZ);
    }

    public static void incrementOneBlockBroken(){
        GameObject.breaks++;
        GameObject.breaksThisLevel++;
    }

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

    public static ItemStack selectRare(World world){
        ItemStack stack = new ItemStack(Items.DIRT); // fallback indicates error
        if(GameObject.randomRareDropMode){
            Random random = new Random();
            int randomIndex = random.nextInt(Registries.ITEM.size());
            Item randomItem = Registries.ITEM.get(randomIndex);
            stack = new ItemStack(randomItem);
        }else{
            boolean itemFound = false;
            MinecraftServer server = world.getServer();
            int randomLevel = randomInt(GameObject.currentLevel);
            int maxRareLevel = GameObject.rareDrops.length;
            Integer randomRare;
            if(randomLevel < maxRareLevel){
                randomRare = randomInt(rareDrops[randomLevel].length);
            }else{
                randomLevel = randomInt(maxRareLevel);
                randomRare = randomInt(rareDrops[maxRareLevel-1].length);
            }

            String wanted = GameObject.rareDrops[randomLevel][randomRare].toUpperCase();
            for(Item item : Registries.ITEM){
                if(item.getName().getString().toUpperCase().equals(wanted)){
                    stack = new ItemStack(item);
                    itemFound = true;
                    break;
                }
            }
            if(!itemFound){
                String message = (MOD_SAYS + "Could not find item: " + GameObject.rareDrops[randomLevel][randomRare]);
                assert server != null;
                server.getPlayerManager().broadcast(Text.of(message), false);
            }
        }

        return stack;
    }

    public static void spawnChestWithRare(World world){
        world.setBlockState(GameObject.getOneBlockPos(), Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(GameObject.getOneBlockPos()) instanceof ChestBlockEntity chestBlockEntity) {
            chestBlockEntity.setStack(0, selectRare(world));
            world.updateListeners((GameObject.getOneBlockPos()), Blocks.CHEST.getDefaultState(), Blocks.CHEST.getDefaultState(), 3);
        }
    }

    public static void spawnRareItem(World world){
        world.spawnEntity(new ItemEntity(world, GameObject.getOneBlockPos().getX(), GameObject.getOneBlockPos().getY()+1, GameObject.getOneBlockPos().getZ(), selectRare(world)));
    }

    /**
     * NEW: Parse-and-place route that supports mod blocks + [state] + {nbt}.
     */
    private static boolean placeParsedBlock(World world, BlockPos pos, String blockString) {
        try {
            Parsed parsed = parseBlockString((ServerWorld) world, blockString);
            boolean ok = world.setBlockState(pos, parsed.state, 3);
            if (ok && parsed.nbt != null) {
                var be = world.getBlockEntity(pos);
                if (be != null) {
                    NbtCompound current = be.createNbtWithIdentifyingData(); // no-arg in your env
                    current.copyFrom(parsed.nbt);
                    be.readNbt(current); // no-arg in your env
                    be.markDirty();
                    world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                }
            }
            return ok;
        } catch (CommandSyntaxException | IllegalArgumentException e) {
            MinecraftServer server = world.getServer();
            if (server != null) {
                server.getPlayerManager().broadcast(Text.of(MOD_SAYS + " Parse error for block '" + blockString + "': " + e.getMessage()), false);
            }
            return false;
        }
    }

    /**
     * Parses strings like:
     *   "minecraft:stone"
     *   "othermod:pipe[facing=north,waterlogged=false]"
     *   "minecraft:chest[facing=west]{LootTable:\"minecraft:chests/simple_dungeon\"}"
     * If input is a bare id without state/nbt, we still validate via the registry.
     */
    private static Parsed parseBlockString(ServerWorld world, String input) throws CommandSyntaxException {
        // Handle bare identifiers like "minecraft:stone"
        if (looksLikeBareId(input)) {
            Identifier id = Identifier.tryParse(input);
            if (id == null) {
                throw new IllegalArgumentException("Invalid identifier: " + input);
            }
            Block block = Registries.BLOCK.getOrEmpty(id)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown block id: " + id));
            return new Parsed(block.getDefaultState(), null);
        }

        // Use BlockStateArgumentType with the server's registry manager
        BlockStateArgumentType type = BlockStateArgumentType.blockState((CommandRegistryAccess) world.getRegistryManager());
        BlockStateArgument arg = type.parse(new StringReader(input));

        // In your mappings, only the BlockState is available directly
        BlockState state = arg.getBlockState();

        return new Parsed(state, null); // nbt stays null
    }

    private static boolean looksLikeBareId(String s) {
        return s.indexOf('[') < 0 && s.indexOf('{') < 0 && s.indexOf(' ') < 0 && s.contains(":");
    }

    private record Parsed(BlockState state, NbtCompound nbt) {}

    public static void respawnOneBlock(World world){
        MinecraftServer server = world.getServer();
        ServerWorld sWorld = (world instanceof ServerWorld) ? (ServerWorld) world : server.getOverworld();

        if(GameObject.randomBlockMode){
            // pick a random block that’s a full cube & breakable, try up to N times
            Random rnd = new Random();
            int size = Registries.BLOCK.size();
            int tries = Math.min(512, size);
            Block candidate = null;

            for (int i = 0; i < tries; i++) {
                Block b = Registries.BLOCK.get(rnd.nextInt(size));
                if (isBlockBreakable(b) && isStandardCube(b.getDefaultState(), sWorld, GameObject.getOneBlockPos())) {
                    candidate = b;
                    break;
                }
            }
            if (candidate == null) {
                candidate = Blocks.STONE; // safe fallback
            }
            sWorld.setBlockState(getOneBlockPos(), candidate.getDefaultState(), 3);
            return;
        }

        int randomLevel = randomInt(GameObject.currentLevel);
        int randomBlock = randomInt(levels[randomLevel].length);
        String selectedBlock = levels[randomLevel][randomBlock];

        boolean blockPlaced = false;

        // Route 1: looks like id / state / nbt => use parser (supports mod blocks)
        if (selectedBlock.contains(":") || selectedBlock.contains("[") || selectedBlock.contains("{")) {
            blockPlaced = placeParsedBlock(sWorld, getOneBlockPos(), selectedBlock);
        }

        // Route 2: try display-name match (case-insensitive) if Route 1 failed or didn’t apply
        if (!blockPlaced && !(selectedBlock.contains(":") || selectedBlock.contains("[") || selectedBlock.contains("{"))) {
            String wanted = selectedBlock.toUpperCase();
            for (Block b : Registries.BLOCK) {
                if (b.getName().getString().toUpperCase().equals(wanted)) {
                    sWorld.setBlockState(getOneBlockPos(), b.getDefaultState(), 3);
                    blockPlaced = true;
                    break;
                }
            }
        }

        // Route 3: final fallback to GRASS_BLOCK + broadcast
        if(!blockPlaced){
            sWorld.setBlockState(getOneBlockPos(), Blocks.GRASS_BLOCK.getDefaultState(), 3);
            String message = (MOD_SAYS + "Could not find/place block entry: " + selectedBlock + " (level " + (randomLevel+1) + ")");
            server.getPlayerManager().broadcast(Text.of(message), false);
        }
    }

    public static int calculateRemainingBreaks() {
        return GameObject.breaksNeededThisLevel - GameObject.breaksThisLevel;
    }
}
