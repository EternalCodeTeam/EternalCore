package com.eternalcode.core;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.event.EternalReloadEvent;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

@Command(name = "eternalcore")
@Permission("eternalcore.eternalcore")
class EternalCoreCommand {

    private static final String RELOAD_MESSAGE = "<b><gradient:#29fbff:#38b3ff>EternalCore:</gradient></b> "
        + "<color:#9d6eef>Configuration has been successfully reloaded in %d ms.</color:#9d6eef>";

    private final ConfigurationManager configurationManager;
    private final MiniMessage miniMessage;
    private final Publisher publisher;

    @Inject
    EternalCoreCommand(ConfigurationManager configurationManager, MiniMessage miniMessage, Publisher publisher) {
        this.configurationManager = configurationManager;
        this.miniMessage = miniMessage;
        this.publisher = publisher;
    }

    @Async
    @Execute(name = "reload")
    @DescriptionDocs(description = "Reloads EternalCore configuration")
    void reload(@Sender Audience audience) {
        long millis = this.reload();
        Component message = this.miniMessage.deserialize(RELOAD_MESSAGE.formatted(millis));

        audience.sendMessage(message);
    }

    private long reload() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.configurationManager.reload();
        this.publisher.publish(new EternalReloadEvent());

        return stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

}
