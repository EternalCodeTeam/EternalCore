package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface NotificationsExtractor {

    List<Notification> extractNotifications(Messages messages);

    static NotificationsExtractor of(NotificationExtractor extractor) {
        return extractor;
    }

    static NotificationsExtractor of(Notification notification) {
        return messages -> Collections.singletonList(notification);
    }
}
