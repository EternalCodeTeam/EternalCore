package com.eternalcode.core;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.util.legacy.Legacy;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Section(route = "eternalcore", aliases = { "eternal" })
@Permission("eternalcore.eternalcore")
class EternalCoreCommand {

    private final ConfigurationManager configurationManager;
    private final MiniMessage miniMessage;
    private final Server server;

    EternalCoreCommand(ConfigurationManager configurationManager, MiniMessage miniMessage, Server server) {
        this.configurationManager = configurationManager;
        this.miniMessage = miniMessage;
        this.server = server;
    }

    @Execute(route = "reload")
    @Permission("eternalcore.reload")
    void reload(Player player) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.configurationManager.reload();

        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        Component deserialize = this.miniMessage.deserialize("<b><gradient:#29fbff:#38b3ff>EternalCore:</gradient></b> <green>Configuration has ben successfully reloaded in " + millis + "ms");
        player.sendMessage(Legacy.SECTION_SERIALIZER.serialize(deserialize));
        this.server.getLogger().info("EternalCore configs has been successfully reloaded in " + millis + "ms");
    }
}
