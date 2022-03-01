package com.eternalcode.core.chat.message;

import com.eternalcode.core.configuration.lang.Messages;


@FunctionalInterface
public interface MessageExtractor {

    String extract(Messages messages);

}
