package com.eternalcode.core.notification.adventure;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.notification.NotificationAnnouncer;
import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import panda.utilities.StringUtils;

@Service
public class AdventureNotificationAnnouncer implements NotificationAnnouncer {

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    @Inject
    public AdventureNotificationAnnouncer(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @Override
    public void announce(Viewer viewer, Notification notification) {
        Component component = this.miniMessage.deserialize(notification.getMessage());

        for (NoticeType type : notification.getTypes()) {
            this.send(this.toAdventureAudience(viewer), type, component);
        }
    }

    @Override
    public void announce(Iterable<Viewer> viewers, Notification notification) {
        Component component = this.miniMessage.deserialize(notification.getMessage());

        for (NoticeType type : notification.getTypes()) {
            for (Viewer viewer : viewers) {
                this.send(this.toAdventureAudience(viewer), type, component);
            }
        }
    }

    private void send(Audience audience, NoticeType type, Component component) {
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
            case DISABLED: return;

            default: audience.sendMessage(component);
        }
    }

    private Audience toAdventureAudience(Viewer viewer) {
        if (viewer.isConsole()) {
            return this.audienceProvider.console();
        }

        return this.audienceProvider.player(viewer.getUniqueId());
    }

}
