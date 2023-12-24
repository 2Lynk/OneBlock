package lynk.oneblock.utils;

import lynk.oneblock.command.Commands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {

    /**
     * Registers all the necessary components for the mod at startup. This method serves as an initialization point
     * for registering various elements of the mod, such as commands, items, blocks, and more. Currently, it is set
     * up to only register commands, but it can be expanded to include other registration calls as needed.
     *
     * Note: This method should be called during the server startup or mod initialization phase to ensure that all
     * mod components are properly registered and available for use in the game.
     *
     * The current implementation includes:
     * - registerCommands(): A method that registers all the commands related to the mod.
     *
     * Additional registration calls for items, blocks, entities, etc., can be added to this method as the mod expands.
     * It's a centralized method to handle all registrations, keeping the mod initialization organized and maintainable.
     */
    public static void registerModStuffs() {
        registerCommands();
    }

    /**
     * Registers all commands for the mod. This method is invoked as part of the mod initialization process,
     * specifically within the 'registerModStuffs' method. It utilizes the CommandRegistrationCallback to register
     * custom commands defined in the Commands class.
     *
     * Note: This method is private and intended to be called only within the GameObject class as part of the mod's
     * setup process. It should not be invoked directly from outside of this setup context.
     *
     * The method executes the following:
     * - CommandRegistrationCallback.EVENT.register(Commands::register): Registers commands through a callback event,
     *   calling a static method in the Commands class that defines the commands for the mod.
     *
     * This registration mechanism ensures that all custom commands associated with the mod are available and functional
     * when the server is running. It's a critical step in making the mod's features accessible to players through in-game
     * commands.
     */
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(Commands::register);
    }
}
