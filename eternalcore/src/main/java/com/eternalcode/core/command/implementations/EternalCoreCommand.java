package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Section(route = "eternalcore", aliases = { "eternal" })
@UsageMessage("&8» &cPoprawne użycie &7/eternalcore <reload>")
@Permission("eternalcore.command.eternalcore")
public class EternalCoreCommand {

    private final ConfigurationManager manager;
    private final Server server;

    public EternalCoreCommand(ConfigurationManager manager, Server server) {
        this.manager = manager;
        this.server = server;
    }

    @Execute(route = "reload")
    @Permission("eternalcore.command.reload")
    public void reload(Player player) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.manager.loadAndRenderConfigs();

        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        player.sendMessage("EternalCore configs has ben successfully reloaded in " + millis + "ms");
        this.server.getLogger().info("EternalCore configs has ben successfully reloaded in " + millis + "ms");
    }
}
