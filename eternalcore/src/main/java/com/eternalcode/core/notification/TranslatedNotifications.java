package com.eternalcode.core.notification;

import com.eternalcode.core.language.Language;

import java.util.List;
import java.util.Map;

record TranslatedNotifications(Map<Language, List<Notification>> messages) {

    List<Notification> forLanguage(Language language) {
        return this.messages.get(language);
    }

    static TranslatedNotifications of(Map<Language, List<Notification>> notifications) {
        return new TranslatedNotifications(notifications);
    }

}
