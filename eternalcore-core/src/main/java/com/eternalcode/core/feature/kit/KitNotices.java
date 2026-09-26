package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.notice.Notice;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.command.CommandSender;

/**
 * Single entry point for kit notices - keeps commands and GUIs free of notice boilerplate.
 */
@Service
public class KitNotices {

    public static final String KIT = "{KIT}";
    public static final String KIT_NAME = "{KIT_NAME}";
    public static final String COOLDOWN = "{COOLDOWN}";
    public static final String PERMISSION = "{PERMISSION}";
    public static final String AMOUNT = "{AMOUNT}";
    public static final String SLOT = "{SLOT}";
    public static final String MAX = "{MAX}";
    public static final String NAME = "{NAME}";
    public static final String COMMAND = "{COMMAND}";
    public static final String PLAYER = "{PLAYER}";

    private final NoticeService noticeService;

    @Inject
    KitNotices(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public void send(CommandSender sender, Function<KitMessages, Notice> message) {
        this.send(sender, message, Map.of());
    }

    public void send(CommandSender sender, Function<KitMessages, Notice> message, Map<String, String> placeholders) {
        EternalCoreBroadcast<Viewer, Translation, ?> broadcast = this.noticeService.create()
            .sender(sender)
            .notice(translation -> message.apply(translation.kit()));

        placeholders.forEach((key, value) -> broadcast.placeholder(key, value));
        broadcast.send();
    }
}
