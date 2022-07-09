package com.eternalcode.core;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;

import java.util.concurrent.TimeUnit;

@Section(route = "eternalcore", aliases = { "eternal" })
@Permission("eternalcore.eternalcore")
class EternalCoreCommand {

    private final ConfigurationManager manager;

    EternalCoreCommand(ConfigurationManager manager) {
        this.manager = manager;
    }

    @Execute(route = "reload")
    @Permission("eternalcore.reload")
    String reload() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.manager.loadAndRenderConfigs();

        return "<green>EternalCore configs has ben successfully reloaded in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms";
    }

}
