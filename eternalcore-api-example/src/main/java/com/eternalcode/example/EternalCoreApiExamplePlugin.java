package com.eternalcode.example;

import com.eternalcode.core.EternalCoreApi;
import com.eternalcode.core.EternalCoreApiProvider;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.example.feature.afk.ApiAfkCommand;
import com.eternalcode.example.feature.afk.ApiAfkListener;
import com.eternalcode.example.feature.catboy.CatBoyListener;
import com.eternalcode.example.feature.ignore.ApiIgnoreCommand;
import com.eternalcode.example.feature.randomteleport.ApiRandomTeleportCommand;
import com.eternalcode.example.feature.randomteleport.ApiRandomTeleportListener;
import com.eternalcode.example.feature.spawn.ApiSpawnCommand;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.message.LiteMessages;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public class EternalCoreApiExamplePlugin extends JavaPlugin {

    private static final String FALLBACK_PREFIX = "eternalcore-api-example";

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Server server = this.getServer();
        EventCaller caller = new EventCaller(this.getServer());

        EternalCoreApi provide = EternalCoreApiProvider.provide();

        this.liteCommands = LiteBukkitFactory.builder(FALLBACK_PREFIX, this, server)
            .message(LiteBukkitMessages.PLAYER_ONLY, input -> "You must be a player to execute this command!")
            .message(LiteBukkitMessages.PLAYER_NOT_FOUND, input -> "Player not found!")
            .message(LiteMessages.MISSING_PERMISSIONS, input -> "You don't have permission to execute this command!")

            .commands(
                new ApiAfkCommand(provide.getAfkService()),
                new ApiIgnoreCommand(provide.getIgnoreService(), caller),
                new ApiRandomTeleportCommand(provide.getRandomTeleportService()),
                new ApiSpawnCommand(provide.getSpawnService())
            )

            .build();

        Stream.of(
            new ApiAfkListener(),
            new CatBoyListener(provide.getCatboyService()),
            new ApiRandomTeleportListener(provide.getRandomTeleportService())
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
