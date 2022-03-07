package com.eternalcode.core.language;

@FunctionalInterface
public interface MessageExtractor {

    String extract(Messages messages);

}
