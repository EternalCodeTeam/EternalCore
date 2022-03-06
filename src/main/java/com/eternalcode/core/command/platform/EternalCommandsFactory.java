package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.AudienceProvider;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteFactory;
import dev.rollczi.litecommands.bind.basic.OriginalSenderBind;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public final class EternalCommandsFactory {

    private EternalCommandsFactory() {}

    public static LiteCommandsBuilder<CommandSender, EternalPlatform> builder(Server server, String fallbackPrefix, AudienceProvider audienceProvider, NotificationAnnouncer announcer) {
        return LiteFactory.<CommandSender, EternalPlatform>builder()
            .bind(Server.class, server)
            .bind(CommandSender.class, new OriginalSenderBind())
            .platform(new EternalPlatform(server, fallbackPrefix, audienceProvider, announcer));
    }

}
