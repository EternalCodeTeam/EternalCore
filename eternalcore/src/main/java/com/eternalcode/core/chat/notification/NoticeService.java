package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.MessageExtractor;
import com.eternalcode.core.language.NotificationExtractor;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;

import javax.annotation.CheckReturnValue;
import java.util.UUID;

public class NoticeService {

    private final LanguageManager languageManager;
    private final ViewerProvider viewerProvider;
    private final NotificationAnnouncer announcer;

    public NoticeService(LanguageManager languageManager, ViewerProvider viewerProvider, NotificationAnnouncer announcer) {
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
        this.announcer = announcer;
    }

    @CheckReturnValue
    public Notice notice() {
        return new Notice(this.languageManager, this.viewerProvider, this.announcer);
    }

    public void player(UUID player, MessageExtractor extractor) {
        this.notice()
            .player(player)
            .message(extractor)
            .send();
    }

    public void viewer(Viewer viewer, MessageExtractor extractor) {
        this.notice()
            .viewer(viewer)
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
