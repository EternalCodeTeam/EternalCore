package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.AudienceProvider;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import dev.rollczi.litecommands.bukkit.LiteBukkitPlatformManager;
import dev.rollczi.litecommands.platform.LitePlatformManager;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

final class EternalPlatform extends LiteBukkitPlatformManager implements LitePlatformManager<CommandSender> {

    EternalPlatform(Server server, String fallbackPrefix, AudienceProvider audienceProvider, NotificationAnnouncer announcer) {
        super(server, fallbackPrefix);

        this.setLiteSenderCreator(new EternalSenderCreator(announcer, audienceProvider));
    }

}
