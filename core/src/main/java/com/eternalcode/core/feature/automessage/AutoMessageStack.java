package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notification.Notification;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

@Contextual
public class AutoMessageStack {

    private List<Notification> notifications = new ArrayList<>();
    private Sound sound;

    public AutoMessageStack(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public AutoMessageStack() {
    }

    public List<Notification> notifications() {
        return this.notifications;
    }

    public Sound sound() {
        return this.sound;
    }

    public static AutoMessageStack withNotification(Notification... notifications) {
        return new AutoMessageStack(List.of(notifications));
    }

    public static AutoMessageStack withNotification(List<Notification> notifications) {
        return new AutoMessageStack(notifications);
    }

    public AutoMessageStack withSound(Sound sound) {
        this.sound = sound;

        return this;
    }
}
