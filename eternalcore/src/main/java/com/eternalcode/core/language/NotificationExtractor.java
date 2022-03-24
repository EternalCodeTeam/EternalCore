package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;

@FunctionalInterface
public interface NotificationExtractor {

    Notification extract(Messages messages);

}
