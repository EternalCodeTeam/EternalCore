package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;

import com.eternalcode.multification.notice.provider.NoticeProvider;

import org.bukkit.command.CommandSender;

import java.util.Map;

@Service
class PunishmentBroadcastService {

    private final NoticeService noticeService;

    @Inject
    PunishmentBroadcastService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    void broadcast(
        NoticeProvider<Translation> broadcastTranslation,
        Map<String, String> placeholders,
        boolean silent,
        String staffPermission
    ) {
        var notice = this.noticeService.create().notice(broadcastTranslation);

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            notice = notice.placeholder(placeholder.getKey(), placeholder.getValue());
        }

        var target = silent
            ? notice.onlinePlayers(staffPermission)
            : notice.all();

        target.send();
    }

    void privateConfirmation(
        NoticeProvider<Translation> privateTranslation,
        Map<String, String> placeholders,
        CommandSender operator
    ) {
        var notice = this.noticeService.create().notice(privateTranslation);

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            notice = notice.placeholder(placeholder.getKey(), placeholder.getValue());
        }

        notice.sender(operator).send();
    }
}
