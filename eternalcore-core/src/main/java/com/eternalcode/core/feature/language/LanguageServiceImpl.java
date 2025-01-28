package com.eternalcode.core.feature.language;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final Map<UUID, Language> cachedLanguages = new ConcurrentHashMap<>();
    private LanguageProvider defaultProvider;

    @Inject
    LanguageServiceImpl(LanguageRepository languageRepository, LanguageProvider defaultProvider) {
        this.languageRepository = languageRepository;
        this.defaultProvider = defaultProvider;
    }

    @Override
    public void setDefaultProvider(LanguageProvider defaultProvider) {
        this.defaultProvider = defaultProvider;
    }

    @Override
    public LanguageProvider getDefaultProvider() {
        return defaultProvider;
    }

    @Override
    public Language getLanguageNow(UUID playerUniqueId) {
        Language language = cachedLanguages.get(playerUniqueId);
        if (language != null) {
            return language;
        }

        return defaultProvider.getDefaultLanguage(playerUniqueId);
    }

    @Override
    public CompletableFuture<Language> getLanguage(UUID playerUniqueId) {
        Language language = cachedLanguages.get(playerUniqueId);
        if (language != null) {
            return CompletableFuture.completedFuture(language);
        }

        return this.languageRepository.findLanguage(playerUniqueId)
            .thenApply(optional -> optional.orElseGet(() -> this.defaultProvider.getDefaultLanguage(playerUniqueId)));
    }

    @Override
    public CompletableFuture<Void> setLanguage(UUID playerUniqueId, Language language) {
        if (language.equals(Language.DEFAULT)) {
            return setDefaultLanguage(playerUniqueId);
        }

        cachedLanguages.put(playerUniqueId, language);
        return languageRepository.saveLanguage(playerUniqueId, language);
    }

    @Override
    public CompletableFuture<Void> setDefaultLanguage(UUID playerUniqueId) {
        cachedLanguages.remove(playerUniqueId);
        return languageRepository.deleteLanguage(playerUniqueId);
    }

    CompletableFuture<Void> loadLanguage(UUID playerUniqueId) {
        return languageRepository.findLanguage(playerUniqueId)
            .thenAccept(language -> language.ifPresent(lang -> cachedLanguages.put(playerUniqueId, lang)));
    }

    CompletableFuture<Void> unloadLanguage(UUID playerUniqueId) {
        return languageRepository.findLanguage(playerUniqueId)
            .thenAccept(language -> cachedLanguages.remove(playerUniqueId));
    }

}
