package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.LanguageManager;
import org.bukkit.command.CommandSender;

public class AudiencesService {

    private final LanguageManager languageManager;
    private final AudienceProvider audienceProvider;
    private final NotificationAnnouncer announcer;

    public AudiencesService(LanguageManager languageManager, AudienceProvider audienceProvider, NotificationAnnouncer announcer) {
        this.languageManager = languageManager;
        this.audienceProvider = audienceProvider;
        this.announcer = announcer;
    }

    public Notice notice() {
        return new Notice(languageManager, audienceProvider, announcer);
    }

    public void sender(CommandSender sender, MessageExtractor extractor) {
        this.notice()
            .sender(sender)
            .message(extractor)
            .send();
    }

    public void console(MessageExtractor extractor) {
        this.notice()
            .console()
            .message(extractor)
            .send();
    }

    public void all(NotificationType type, MessageExtractor extractor) {
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
