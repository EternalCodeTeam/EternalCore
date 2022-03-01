package com.eternalcode.core.chat.notification;

import com.eternalcode.core.configuration.lang.Messages;


@FunctionalInterface
public interface MessageExtractor {

    String extract(Messages messages);

}
