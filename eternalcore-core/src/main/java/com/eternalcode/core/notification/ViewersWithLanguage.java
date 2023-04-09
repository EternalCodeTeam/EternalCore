package com.eternalcode.core.notification;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.viewer.Viewer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

record ViewersWithLanguage(Map<Language, Set<Viewer>> viewersByLanguage) {

    Set<Language> getLanguages() {
        return this.viewersByLanguage.keySet();
    }

    Set<Viewer> getViewers(Language language) {
        return this.viewersByLanguage.get(language);
    }

    static ViewersWithLanguage of(Collection<Viewer> viewers) {
        Map<Language, Set<Viewer>> viewersByLanguage = new HashMap<>();

        for (Viewer viewer : viewers) {
            viewersByLanguage
                .computeIfAbsent(viewer.getLanguage(), key -> new HashSet<>())
                .add(viewer);
        }

        return new ViewersWithLanguage(viewersByLanguage);
    }

}
