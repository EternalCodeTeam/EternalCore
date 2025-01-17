package com.eternalcode.core.feature.language;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface LanguageService {

    void setDefaultProvider(LanguageProvider defaultProvider);

    LanguageProvider getDefaultProvider();

    CompletableFuture<Language> getLanguage(UUID playerUniqueId);

    Language getLanguageNow(UUID playerUniqueId);

    CompletableFuture<Void> setLanguage(UUID playerUniqueId, Language language);

    CompletableFuture<Void> setDefaultLanguage(UUID playerUniqueId);

}
