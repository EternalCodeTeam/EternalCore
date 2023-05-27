package com.eternalcode.core.notice;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.viewer.Viewer;

import java.util.*;

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
