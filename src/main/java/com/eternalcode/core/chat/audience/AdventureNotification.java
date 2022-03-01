package com.eternalcode.core.chat.audience;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.chat.notification.NotificationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Set;
import java.util.function.Function;

public class AdventureNotification extends Notification {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final Component component;

    public AdventureNotification(Component component, NotificationType... types) {
        super(MINI_MESSAGE.serialize(component), types);
        this.component = component;
    }

    public AdventureNotification(Component component, Set<NotificationType> types) {
        super(MINI_MESSAGE.serialize(component), types);
        this.component = component;
    }

    public void notify(Audience audience) {
        for (NotificationType type : types) {
            type.notify(audience.getAdventureAudience(), this.component);
        }
    }

    @Override
    public Notification edit(Function<String, String> edit) {
        return this;
    }

    @Override
    public AdventureNotification toAdventure(Function<String, Component> parser) {
        return this;
    }

}
