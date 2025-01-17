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
    public Language getLanguageNow(UUID player) {
        Language language = cachedLanguages.get(player);
        if (language != null) {
            return language;
        }

        return defaultProvider.getDefaultLanguage(player);
    }

    @Override
    public CompletableFuture<Language> getLanguage(UUID player) {
        Language language = cachedLanguages.get(player);
        if (language != null) {
            return CompletableFuture.completedFuture(language);
        }

        return this.languageRepository.findLanguage(player)
            .thenApply(optional -> optional.orElseGet(() -> this.defaultProvider.getDefaultLanguage(player)));
    }

    @Override
    public CompletableFuture<Void> setLanguage(UUID player, Language language) {
        if (language.equals(Language.DEFAULT)) {
            return setDefaultLanguage(player);
        }

        cachedLanguages.put(player, language);
        return languageRepository.saveLanguage(player, language);
    }

    @Override
    public CompletableFuture<Void> setDefaultLanguage(UUID player) {
        cachedLanguages.remove(player);
        return languageRepository.deleteLanguage(player);
    }

    CompletableFuture<Void> loadLanguage(UUID uniqueId) {
        return languageRepository.findLanguage(uniqueId)
            .thenAccept(language -> language.ifPresent(lang -> cachedLanguages.put(uniqueId, lang)));
    }

    CompletableFuture<Void> unloadLanguage(UUID uniqueId) {
        return languageRepository.findLanguage(uniqueId)
            .thenAccept(language -> cachedLanguages.remove(uniqueId));
    }

}
