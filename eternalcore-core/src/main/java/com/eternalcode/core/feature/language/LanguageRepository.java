package com.eternalcode.core.feature.language;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface LanguageRepository {

    CompletableFuture<Optional<Language>> findLanguage(UUID player);

    CompletableFuture<Void> saveLanguage(UUID player, Language language);

    CompletableFuture<Void> deleteLanguage(UUID player);

}
