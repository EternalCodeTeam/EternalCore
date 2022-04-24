package com.eternalcode.core.chat.adventure;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.chat.notification.NotificationAnnouncer;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import panda.utilities.StringUtils;

public class AdventureNotificationAnnouncer implements NotificationAnnouncer {

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public AdventureNotificationAnnouncer(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @Override
    public void announce(Audience audience, Notification notification) {
        Component component = this.miniMessage.deserialize(notification.getMessage());

        for (NoticeType type : notification.getTypes()) {
            this.send(this.toAdventureAudience(audience), type, component);
        }
    }

    @Override
    public void announce(Iterable<Audience> audiences, Notification notification) {
        Component component = this.miniMessage.deserialize(notification.getMessage());

        for (NoticeType type : notification.getTypes()) {
            for (Audience audience : audiences) {
                this.send(this.toAdventureAudience(audience), type, component);
            }
        }
    }

    private void send(net.kyori.adventure.audience.Audience audience, NoticeType type, Component component) {
        switch (type) {
            case CHAT:
                audience.sendMessage(component);
                return;
            case ACTIONBAR:
                audience.sendActionBar(component);
                return;
            case TITLE:
                audience.showTitle(Title.title(component, Component.text(StringUtils.EMPTY)));
                return;
            case SUBTITLE:
                audience.showTitle(Title.title(Component.text(StringUtils.EMPTY), component));
                return;
            case NONE:
        }
    }

    private net.kyori.adventure.audience.Audience toAdventureAudience(Audience audience) {
        if (audience.isConsole()) {
            return this.audienceProvider.console();
        }

        return this.audienceProvider.player(audience.getUuid());
    }

}
