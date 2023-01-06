package com.eternalcode.core.notification.extractor;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface NotificationsExtractor {

    List<Notification> extractNotifications(Translation translation);

    static NotificationsExtractor of(NotificationExtractor extractor) {
        return extractor;
    }

    static NotificationsExtractor of(Notification notification) {
        return translation -> Collections.singletonList(notification);
    }

}
