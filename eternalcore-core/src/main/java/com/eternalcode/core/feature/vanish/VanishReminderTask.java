package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.notice.NoticeService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Task(period = 1, delay = 1, unit = TimeUnit.SECONDS)
class VanishReminderTask implements Runnable {

    private final NoticeService noticeService;
    private final VanishService vanishService;

    @Inject
    VanishReminderTask(VanishService vanishService, NoticeService noticeService) {
        this.vanishService = vanishService;
        this.noticeService = noticeService;
    }

    @Override
    public void run() {
        for (UUID vanishedPlayer : this.vanishService.getVanishedPlayers()) {
            this.noticeService.player(vanishedPlayer, message -> message.vanish().currentlyInVanish());
        }
    }
}
