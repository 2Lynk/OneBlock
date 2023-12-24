package lynk.oneblock;

import lynk.oneblock.server.GameObject;
import lynk.oneblock.utils.ModRegistries;
import lynk.oneblock.utils.Server;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static lynk.oneblock.server.Events.*;
import static lynk.oneblock.server.GameObject.getOneBlockPos;
import static lynk.oneblock.server.GameObject.saveToJson;
import static lynk.oneblock.utils.Server.checkAndNotifySpawnPoint;

public class Oneblock implements ModInitializer {
	public static final String MOD_ID = "oneblock";
	public static final String MOD_SAYS = MOD_ID + ": ";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
		// Check if the block placed is at the target position
		if (getOneBlockPos().equals(hitResult.getBlockPos())) {
			// Your logic when a block is placed at the target position
			// e.g., notify the player or trigger some other action
			System.out.println(player.getName().getString() + " placed a block at the target position!");
		}

		return ActionResult.PASS; // Don't interfere with normal block placement
	}

	/**
	 * Initializes the mod when Minecraft starts. This method is the entry point for the mod and is called during
	 * the game's startup process. It sets up various components and event listeners essential for the mod's functionality.
	 *
	 * The method performs the following actions:
	 * - Logs an initialization message to indicate the mod is being loaded.
	 * - Calls 'ModRegistries.registerModStuffs()' to register mod-specific items, blocks, commands, etc.
	 * - Registers a server lifecycle event listener to handle custom actions when the server starts.
	 * - Registers an event listener for player block break events to handle specific game mechanics when a block
	 *   at a certain position is broken by a player.
	 * - Registers a server lifecycle event listener to perform actions such as saving data to JSON when the server stops.
	 *
	 * Note: This method should not be manually invoked. It is automatically called by the game engine during the
	 * mod loading process.
	 *
	 * This initialization sequence is critical for setting up the mod's core functionality and integrating it seamlessly
	 * into the game.
	 */
	@Override
	public void onInitialize() {
		LOGGER.info(MOD_SAYS + "Hello Fabric world!");
		ModRegistries.registerModStuffs();

		ServerLifecycleEvents.SERVER_STARTED.register(Server::start);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.player;
			checkAndNotifySpawnPoint(player);
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			if(Objects.equals(pos, getOneBlockPos())){
				handleOneBlockBreak(world);
			}
		});

		ServerLifecycleEvents.SERVER_STOPPED.register((MinecraftServer server) -> {
			saveToJson();
		});

		UseBlockCallback.EVENT.register(Server::interact);
	}
}