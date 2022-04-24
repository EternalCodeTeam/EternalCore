package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.AudienceProvider;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import dev.rollczi.litecommands.platform.LiteSender;
import dev.rollczi.litecommands.platform.LiteSenderCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

final class EternalSenderCreator implements LiteSenderCreator<CommandSender> {

    private final NotificationAnnouncer announcer;
    private final AudienceProvider audienceProvider;

    public EternalSenderCreator(NotificationAnnouncer announcer, AudienceProvider audienceProvider) {
        this.announcer = announcer;
        this.audienceProvider = audienceProvider;
    }

    @Override
    public LiteSender create(CommandSender sender) {
        if (!(sender instanceof ConsoleCommandSender) && !(sender instanceof Player)) {
            throw new IllegalStateException();
        }

        Audience audience = sender instanceof Player player
            ? this.audienceProvider.player(player.getUniqueId())
            : this.audienceProvider.console();

        return new EternalSender(sender, audience, this.announcer);
    }

}
