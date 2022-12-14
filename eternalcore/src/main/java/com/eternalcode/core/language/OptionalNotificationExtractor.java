package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;
import panda.std.Option;

@FunctionalInterface
public interface OptionalNotificationExtractor {

    Option<Notification> extract(Messages messages);

}
