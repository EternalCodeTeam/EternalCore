package com.eternalcode.core.chat.notification;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;

import java.util.function.BiConsumer;

public enum NotificationType {

    CHAT(Audience::sendMessage),
    ACTIONBAR(Audience::sendActionBar),
    TITLE((audience, component) -> audience.showTitle(Title.title(component, Component.text(StringUtils.EMPTY)))),
    SUBTITLE((audience, component) -> audience.showTitle(Title.title(Component.text(StringUtils.EMPTY), component))),
    NONE((player, component) -> {});

    private final BiConsumer<Audience, Component> consumer;

    NotificationType(BiConsumer<Audience, Component> consumer) {
        this.consumer = consumer;
    }

    public void notify(Audience audience, Component component) {
        this.consumer.accept(audience, component);
    }

}
