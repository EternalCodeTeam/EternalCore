package com.eternalcode.core.chat.notification;

import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Notification {

    protected final Set<NotificationType> types = new HashSet<>();
    protected final String message;

    Notification(String message, NotificationType... types) {
        this.message = message;
        this.types.addAll(Arrays.asList(types));
    }

    Notification(String message, Set<NotificationType> types) {
        this.message = message;
        this.types.addAll(types);
    }

    public Notification edit(Function<String, String> edit) {
        return new Notification(edit.apply(this.message), types);
    }

    public AdventureNotification toAdventure(Function<String, Component> parser) {
        return new AdventureNotification(parser.apply(this.message), types);
    }

    public static Notification of(String message, NotificationType... types) {
        return new Notification(message, types);
    }

}
