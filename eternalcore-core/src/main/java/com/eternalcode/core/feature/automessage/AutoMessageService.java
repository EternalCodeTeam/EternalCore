package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.reactive.Completable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class AutoMessageService {

    private final AutoMessageRepository repository;
    private final AutoMessageSettings settings;
    private final NoticeService noticeService;
    private final Scheduler scheduler;
    private final Server server;

    private final AtomicInteger broadcastCount = new AtomicInteger(0);

    public AutoMessageService(AutoMessageRepository repository, AutoMessageSettings settings, NoticeService noticeService, Scheduler scheduler, Server server) {
        this.repository = repository;
        this.settings = settings;
        this.noticeService = noticeService;
        this.scheduler = scheduler;
        this.server = server;

        this.tick();
    }

    public Completable<Boolean> switchReciving(UUID uniqueId) {
        return this.repository.switchReciving(uniqueId);
    }

    public void broadcastNextMessage() {
        List<UUID> onlineUniqueIds = this.server.getOnlinePlayers().stream()
            .map(Entity::getUniqueId)
            .toList();

        this.repository.findRecivers(onlineUniqueIds).then(recivers -> {
            if (recivers.isEmpty()) {
                return;
            }

            this.noticeService.create()
                .players(recivers)
                .noticeOption(translation -> nextAutoMessage(translation.autoMessage()))
                .send();
        });
    }

    private void tick() {
        this.scheduler.laterAsync(this::tick, this.settings.interval());

        this.broadcastNextMessage();
    }

    private Option<Notice> nextAutoMessage(Translation.AutoMessageSection messageSection) {
        List<Notice> messages = messageSection.messages();

        if (messages.isEmpty()) {
            return Option.none();
        }

        if (this.settings.drawMode() == AutoMessageSettings.DrawMode.RANDOM) {
            return RandomUtil.randomElement(messages);
        }

        int index = this.broadcastCount.getAndIncrement() % messages.size();

        return Option.of(messages.get(index));
    }
}
