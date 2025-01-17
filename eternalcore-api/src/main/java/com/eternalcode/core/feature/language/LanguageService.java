package com.eternalcode.core.feature.language;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface LanguageService {

    void setDefaultProvider(LanguageProvider defaultProvider);

    LanguageProvider getDefaultProvider();

    CompletableFuture<Language> getLanguage(UUID player);

    Language getLanguageNow(UUID player);

    CompletableFuture<Void> setLanguage(UUID player, Language language);

    CompletableFuture<Void> setDefaultLanguage(UUID player);

}
