package com.eternalcode.core.notification.extractor;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import panda.std.Option;

@FunctionalInterface
public interface OptionalNotificationExtractor {

    Option<Notification> extract(Translation translation);

}
