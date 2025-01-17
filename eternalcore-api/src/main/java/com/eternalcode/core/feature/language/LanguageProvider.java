package com.eternalcode.core.feature.language;

import java.util.UUID;

public interface LanguageProvider {

    Language getDefaultLanguage(UUID player);

}
