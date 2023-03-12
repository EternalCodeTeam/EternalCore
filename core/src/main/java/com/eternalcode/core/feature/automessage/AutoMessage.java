package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notification.Notification;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

@Contextual
public class AutoMessage {

    private List<Notification> notifications = new ArrayList<>();
    private Sound sound;

    public AutoMessage(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public AutoMessage() {
    }

    public List<Notification> notifications() {
        return this.notifications;
    }

    public Sound sound() {
        return this.sound;
    }

    public static AutoMessage withNotification(Notification... notifications) {
        return new AutoMessage(List.of(notifications));
    }

    public static AutoMessage withNotification(List<Notification> notifications) {
        return new AutoMessage(notifications);
    }

    public AutoMessage withSound(Sound sound) {
        this.sound = sound;

        return this;
    }
}
