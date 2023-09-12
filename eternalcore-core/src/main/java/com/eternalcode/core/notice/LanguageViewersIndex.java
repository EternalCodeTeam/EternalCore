package com.eternalcode.core.notice;

import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.viewer.Viewer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class LanguageViewersIndex {

    private final Map<Language, Set<Viewer>> viewersByLanguage;

    private LanguageViewersIndex(Map<Language, Set<Viewer>> viewersByLanguage) {
        this.viewersByLanguage = viewersByLanguage;
    }

    Set<Language> getLanguages() {
        return this.viewersByLanguage.keySet();
    }

    Set<Viewer> getViewers(Language language) {
        return this.viewersByLanguage.get(language);
    }

    static LanguageViewersIndex of(Collection<Viewer> viewers) {
        Map<Language, Set<Viewer>> viewersByLanguage = new HashMap<>();

        for (Viewer viewer : viewers) {
            viewersByLanguage
                .computeIfAbsent(viewer.getLanguage(), key -> new HashSet<>())
                .add(viewer);
        }

        return new LanguageViewersIndex(viewersByLanguage);
    }

}
