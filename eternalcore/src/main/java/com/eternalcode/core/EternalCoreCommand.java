package com.eternalcode.core;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Section(route = "eternalcore", aliases = { "eternal" })
@Permission("eternalcore.eternalcore")
class EternalCoreCommand {

    private final ConfigurationManager manager;
    private final Server server;

    EternalCoreCommand(ConfigurationManager manager, Server server) {
        this.manager = manager;
        this.server = server;
    }
    @Execute(route = "reload")
    @Permission("eternalcore.reload")
    void reload(Player player) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.manager.loadAndRenderConfigs();

        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        player.sendMessage("EternalCore configs has ben successfully reloaded in " + millis + "ms");
        this.server.getLogger().info("EternalCore configs has ben successfully reloaded in " + millis + "ms");
    }
}
