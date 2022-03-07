package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import com.eternalcode.core.chat.notification.NoticeType;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.command.CommandSender;

final class EternalSender implements LiteSender {

    private final NotificationAnnouncer announcer;
    private final CommandSender commandSender;
    private final Audience audience;

    public EternalSender(CommandSender commandSender, Audience audience, NotificationAnnouncer announcer) {
        this.announcer = announcer;
        this.commandSender = commandSender;
        this.audience = audience;
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        announcer.announce(audience, Notification.of(message, NoticeType.CHAT));
    }

    @Override
    public CommandSender getSender() {
        return commandSender;
    }

}
