package com.eternalcode.core.notification.extractor;

import com.eternalcode.core.translation.Translation;

@FunctionalInterface
public interface TranslatedMessageExtractor {

    String extract(Translation translation);

}
