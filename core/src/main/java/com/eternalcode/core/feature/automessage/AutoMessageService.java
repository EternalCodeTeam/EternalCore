package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.util.RandomUtil;
import panda.std.Option;

import java.util.List;

public class AutoMessageService {

    private final AutoMessageSettings settings;
    private final NoticeService noticeService;

    private long broadcastCount = 0;

    public AutoMessageService(AutoMessageSettings settings, NoticeService noticeService) {
        this.settings = settings;
        this.noticeService = noticeService;
    }

    public void broadcastNextMessage() {
        noticeService.create()
            .onlinePlayers()
            .noticeOption(translation -> nextAutoMessage(translation.autoMessage()))
            .send();

        broadcastCount++;
    }

    private Option<AutoMessage> nextAutoMessage(Translation.AutoMessageSection messageSection) {
        List<AutoMessage> messages = messageSection.messages();

        if (messages.isEmpty()) {
            return Option.none();
        }

        if (this.settings.drawMode() == AutoMessageSettings.DrawMode.RANDOM) {
            return RandomUtil.randomElement(messages);
        }

        int index = (int) (broadcastCount % messages.size());

        return Option.of(messages.get(index));
    }
}
