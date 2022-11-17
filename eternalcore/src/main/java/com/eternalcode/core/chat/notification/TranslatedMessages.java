package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Language;

import java.util.List;
import java.util.Map;

record TranslatedMessages(Map<Language, List<Notification>> messages) {

    List<Notification> getNotifications(Language language) {
        return this.messages.get(language);
    }

    static TranslatedMessages of(Map<Language, List<Notification>> messages) {
        return new TranslatedMessages(messages);
    }

}
