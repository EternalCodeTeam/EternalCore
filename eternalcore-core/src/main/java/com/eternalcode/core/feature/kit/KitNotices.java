package com.eternalcode.core.feature.kit;

import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.shared.Formatter;
import java.util.function.Function;
import org.bukkit.command.CommandSender;

@Service
public class KitNotices {

    public static final String KIT = "{KIT}";
    public static final String KIT_NAME = "{KIT_NAME}";
    public static final String DESCRIPTION = "{DESCRIPTION}";
    public static final String ITEMS = "{ITEMS}";
    public static final String COOLDOWN = "{COOLDOWN}";
    public static final String KIT_COOLDOWN = "{KIT_COOLDOWN}";
    public static final String PLAYTIME = "{PLAYTIME}";
    public static final String WORLDS = "{WORLDS}";
    public static final String GAME_MODES = "{GAME_MODES}";
    public static final String TYPE = "{TYPE}";
    public static final String STATUS = "{STATUS}";
    public static final String CLAIMS = "{CLAIMS}";
    public static final String PERMISSION = "{PERMISSION}";
    public static final String AMOUNT = "{AMOUNT}";
    public static final String SLOT = "{SLOT}";
    public static final String PLAYER = "{PLAYER}";
    public static final String COMMAND = "{COMMAND}";
    public static final String MAX = "{MAX}";
    public static final String INDEX = "{INDEX}";
    public static final String LINE = "{LINE}";

    private final NoticeService noticeService;

    @Inject
    KitNotices(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public void send(CommandSender sender, Function<KitMessages, Notice> message) {
        this.send(sender, message, new Formatter());
    }

    public void send(CommandSender sender, Function<KitMessages, Notice> message, Formatter formatter) {
        this.noticeService.create()
            .sender(sender)
            .notice(translation -> message.apply(translation.kit()))
            .formatter(formatter)
            .send();
    }
}
