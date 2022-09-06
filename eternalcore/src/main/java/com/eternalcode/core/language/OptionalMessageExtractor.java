package com.eternalcode.core.language;

import panda.std.Option;

@FunctionalInterface
public interface OptionalMessageExtractor {

    Option<String> extract(Messages messages);

}
