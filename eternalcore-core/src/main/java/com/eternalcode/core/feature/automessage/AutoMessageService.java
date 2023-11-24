package com.eternalcode.core.feature.automessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.util.RandomUtil;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import panda.std.Option;
import panda.std.reactive.Completable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@FeatureDocs(name = "AutoMessage", description = "Automatically sends messages to players at a given time interval.")
@Service
class AutoMessageService {

    private final AutoMessageRepository repository;
    private final AutoMessageSettings settings;
    private final NoticeService noticeService;
    private final Scheduler scheduler;
    private final Server server;

    private final AtomicInteger broadcastCount = new AtomicInteger(0);

    @Inject
    AutoMessageService(AutoMessageRepository repository, AutoMessageSettings settings, NoticeService noticeService, Scheduler scheduler, Server server) {
        this.repository = repository;
        this.settings = settings;
        this.noticeService = noticeService;
        this.scheduler = scheduler;
        this.server = server;

        this.tick();
    }

    Completable<Boolean> switchReceiving(UUID uniqueId) {
        return this.repository.switchReceiving(uniqueId);
    }

    public void broadcastNextMessage() {
        Set<UUID> onlineUniqueIds = this.server.getOnlinePlayers().stream()
            .map(Entity::getUniqueId)
            .collect(Collectors.toSet());

        this.repository.findReceivers(onlineUniqueIds).then(receivers -> {
            if (receivers.isEmpty()) {
                return;
            }

            this.noticeService.create()
                .players(receivers)
                .noticeOption(translation -> this.nextAutoMessage(translation.autoMessage()))
                .send();
        });
    }

    private void tick() {
        this.scheduler.laterAsync(this::tick, this.settings.interval());

        if (this.settings.enabled()) {
            this.broadcastNextMessage();
        }
    }

    private Option<Notice> nextAutoMessage(Translation.AutoMessageSection messageSection) {
        Collection<Notice> messages = messageSection.messages();

        if (messages.isEmpty()) {
            return Option.none();
        }

        if (this.settings.drawMode() == AutoMessageSettings.DrawMode.RANDOM) {
            return RandomUtil.randomElement(messages);
        }

        int index = this.broadcastCount.getAndIncrement() % messages.size();

        return Option.ofOptional(messages.stream().skip(index).findFirst());
    }
}
