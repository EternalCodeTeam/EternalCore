package com.eternalcode.core.chat.audience;

import com.eternalcode.core.chat.message.MessageExtractor;
import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.chat.notification.NotificationExtractor;
import com.eternalcode.core.chat.notification.NotificationType;
import com.eternalcode.core.language.LanguageManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class AudiencesService {

    private final LanguageManager languageManager;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public AudiencesService(LanguageManager languageManager, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.languageManager = languageManager;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    public Notice notice() {
        return new Notice(languageManager, audienceProvider, miniMessage);
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
