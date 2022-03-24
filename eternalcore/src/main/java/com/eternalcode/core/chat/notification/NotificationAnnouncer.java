package com.eternalcode.core.chat.notification;

public interface NotificationAnnouncer {

    void announce(Audience audience, Notification notification);

    void announce(Iterable<Audience> audiences, Notification notification);

}
