package lynk.oneblock.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import lynk.oneblock.server.GameObject;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import static lynk.oneblock.server.GameObject.calculateRemainingBreaks;

public class Commands {

    /**
     * Displays information about the current state of the OneBlock game. This includes the current level,
     * the number of breaks achieved, breaks required for the current level, and the maximum level.
     *
     * @param source The command source, typically a player or the console.
     * @return An integer indicating the command execution status, usually 1 for success.
     *
     * This method is invoked by the 'info' command and provides players with an overview of their current
     * progress in the game.
     */
    public static Integer displayInfo(ServerCommandSource source) {
        String message = null;
        if(GameObject.currentLevel == GameObject.levels.length){
            message = "====== OneBlock info ====== \n" +
                    "OneBlock level " + GameObject.currentLevel + " with " + GameObject.breaks + " breaks of the OneBlock. \n" +
                    "Total breaks per level multiplies by " + GameObject.levelMultiplier + " per level.\n" +
                    "Maximum level is " + GameObject.levels.length + ".\n";
        }else{
            message = "====== OneBlock info ====== \n" +
                    "OneBlock level " + GameObject.currentLevel + " with " + GameObject.breaks + " breaks of the OneBlock. \n" +
                    GameObject.breaksNeededThisLevel + " breaks needed this level with " + calculateRemainingBreaks() + " breaks remaining. \n" +
                    "Total breaks per level multiplies by " + GameObject.levelMultiplier + " per level.\n" +
                    "Maximum level is " + GameObject.levels.length + ".\n";
        }

        if(GameObject.levelNames[GameObject.currentLevel-1] != null){
            message += "Current theme is: " + GameObject.levelNames[GameObject.currentLevel-1] + "\n";
        }
        message += "The following blocks are added this level: \n";
        for (int i = 0; i < GameObject.levels[GameObject.currentLevel-1].length; i++) {
            message += GameObject.levels[GameObject.currentLevel-1][i] + ", ";
        }
        message += "\n=========================";

        source.getPlayer().sendMessage(Text.of(message), false);

        return 1;
    }

    /**
     * Shows detailed information about a specific level in the OneBlock game, including the theme and the
     * types of blocks added at that level.
     *
     * @param source The command source, typically a player or the console.
     * @param level The level number for which information is to be displayed.
     * @return An integer indicating the command execution status, usually 1 for success.
     *
     * This method is triggered by the 'level' command, providing players with insights into the specific
     * characteristics of any given level.
     */
    public static Integer showLevel(ServerCommandSource source, Integer level) {
        String message = "====== OneBlock level info ======\n";

        if(GameObject.levelNames[level-1] != null){
            message += "The theme for level " + level + " is: " + GameObject.levelNames[level-1] + "\n";
        }
        message += "The following blocks are added this level: \n";
        for (int i = 0; i < GameObject.levels[level-1].length; i++) {
            message += GameObject.levels[level-1][i] + ", ";
        }

        source.getPlayer().sendMessage(Text.of(message), false);

        return 1;
    }

    /**
     * Displays credits for the OneBlock mod, including the creator's information and relevant URLs.
     *
     * @param source The command source, typically a player or the console.
     * @return An integer indicating the command execution status, usually 1 for success.
     *
     * This method is used to give credit to the mod creator and provide links to additional resources or
     * websites related to the mod. It is triggered by the 'credits' command.
     */
    public static Integer displayCredits(ServerCommandSource source) {
        String message = "====== OneBlock credits ======\n";
        message += "Made by: 2Lynk \n";
        message += "https://2lynk.dev/ \n";
        message += "https://modrinth.com/mod/theoneblock \n";
        source.getPlayer().sendMessage(Text.of(message), false);

        return 1;
    }

    /**
     * Registers all the commands related to the OneBlock mod. This includes commands for displaying game info,
     * level details, and mod credits.
     *
     * @param dispatcher The command dispatcher that handles command registration.
     * @param commandRegistryAccess Access to the command registry.
     * @param registrationEnvironment The environment where the registration is taking place.
     *
     * This method sets up the command structure for the mod, associating command strings with their respective
     * execution logic.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                CommandManager.literal("oneblock")
                        .requires(source -> source.hasPermissionLevel(0))
                        .then(CommandManager.literal("info")
                                .executes(context -> {
                                        return displayInfo((ServerCommandSource) context.getSource());
                                })
                        )
                        .then(CommandManager.literal("level")
                                .then(CommandManager.argument("level", IntegerArgumentType.integer(1)).executes((context -> {
                                    return showLevel((ServerCommandSource) context.getSource(), IntegerArgumentType.getInteger(context, "level"));
                                })))
                        )
                        .then(CommandManager.literal("credits")
                                .executes(context -> {
                                    return displayCredits((ServerCommandSource) context.getSource());
                                })
                        )
        );
    }
}
