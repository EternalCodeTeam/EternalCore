package com.eternalcode.core.notice;

import com.eternalcode.core.language.Language;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class TranslatedNoticesIndex {

    private final Map<Language, List<Notice>> messages;

    TranslatedNoticesIndex(Map<Language, List<Notice>> messages) {
        this.messages = messages;
    }

    List<Notice> forLanguage(Language language) {
        return this.messages.get(language);
    }

    static TranslatedNoticesIndex of(Collection<Language> languages, Function<Language, List<Notice>> messages) {
        Map<Language, List<Notice>> messagesByLanguage = languages.stream()
            .collect(Collectors.toMap(Function.identity(), messages));

        return new TranslatedNoticesIndex(messagesByLanguage);
    }

}
