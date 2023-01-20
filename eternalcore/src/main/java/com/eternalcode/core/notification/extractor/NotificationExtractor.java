package com.eternalcode.core.notification.extractor;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("FunctionalInterfaceMethodChanged")
@FunctionalInterface
public interface NotificationExtractor extends NotificationsExtractor {

    Notification extract(Translation translation);

    @Override
    default List<Notification> extractNotifications(Translation translation) {
        return Collections.singletonList(this.extract(translation));
    }

}
