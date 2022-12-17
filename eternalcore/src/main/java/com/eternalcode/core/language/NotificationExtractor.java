package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface NotificationExtractor extends NotificationsExtractor {

    Notification extract(Messages messages);

    @Override
    default List<Notification> extractNotifications(Messages messages) {
        return Collections.singletonList(this.extract(messages));
    }
}
