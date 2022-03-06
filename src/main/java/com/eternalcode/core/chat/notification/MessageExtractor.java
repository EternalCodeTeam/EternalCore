package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Messages;

@FunctionalInterface
public interface MessageExtractor {

    String extract(Messages messages);

}
