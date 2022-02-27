package com.eternalcode.core.chat.audience;

import com.eternalcode.core.configuration.lang.Messages;

@FunctionalInterface
public interface NotificationExtractor {

    Notification extract(Messages messages);

}
