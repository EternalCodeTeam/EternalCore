package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.MessageExtractor;
import com.eternalcode.core.language.NotificationExtractor;

import java.util.UUID;

public class NoticeService {

    private final LanguageManager languageManager;
    private final AudienceProvider audienceProvider;
    private final NotificationAnnouncer announcer;

    public NoticeService(LanguageManager languageManager, AudienceProvider audienceProvider, NotificationAnnouncer announcer) {
        this.languageManager = languageManager;
        this.audienceProvider = audienceProvider;
        this.announcer = announcer;
    }

    public Notice notice() {
        return new Notice(this.languageManager, this.audienceProvider, this.announcer);
    }

    public void player(UUID player, MessageExtractor extractor) {
        this.notice()
            .player(player)
            .message(extractor)
            .send();
    }

    public void audience(Audience audience, MessageExtractor extractor) {
        this.notice()
            .audience(audience)
            .message(extractor)
            .send();
    }

    public void console(MessageExtractor extractor) {
        this.notice()
            .console()
            .message(extractor)
            .send();
    }

    public void all(NoticeType type, MessageExtractor extractor) {
        this.notice()
            .all()
            .notice(type, extractor)
            .send();
    }

    public void all(NotificationExtractor extractor) {
        this.notice()
            .all()
            .notice(extractor)
            .send();
    }

    public void all(MessageExtractor extractor) {
        this.notice()
            .all()
            .message(extractor)
            .send();
    }

}
