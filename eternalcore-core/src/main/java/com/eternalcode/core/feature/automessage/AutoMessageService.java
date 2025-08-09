package com.eternalcode.core.feature.automessage;

import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.automessage.messages.AutoMessageMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.entity.Entity;

@Service
class AutoMessageService {

    private final AutoMessageRepository repository;
    private final AutoMessageSettings settings;
    private final NoticeService noticeService;
    private final AutoMessageSettings autoMessageSettings;
    private final Scheduler scheduler;
    private final Server server;

    private final AtomicInteger broadcastCount = new AtomicInteger(0);

    @Inject
    AutoMessageService(
        AutoMessageRepository repository,
        AutoMessageSettings settings,
        NoticeService noticeService,
        AutoMessageSettings autoMessageSettings,
        Scheduler scheduler,
        Server server
    ) {
        this.repository = repository;
        this.settings = settings;
        this.noticeService = noticeService;
        this.autoMessageSettings = autoMessageSettings;
        this.scheduler = scheduler;
        this.server = server;

        this.tick();
    }

    CompletableFuture<Boolean> switchReceiving(UUID uniqueId) {
        return this.repository.switchReceiving(uniqueId);
    }

    private void tick() {
        this.scheduler.timerAsync(
            () -> {
                if (this.settings.enabled()) {
                    this.broadcastNextMessage();
                }
            }, this.settings.interval(), this.settings.interval());
    }

    public void broadcastNextMessage() {
        Set<UUID> onlineUniqueIds = this.server.getOnlinePlayers().stream()
            .map(Entity::getUniqueId)
            .collect(Collectors.toSet());

        int requiredReceivers = this.autoMessageSettings.minPlayers();
        if (onlineUniqueIds.size() < requiredReceivers) {
            return;
        }

        this.repository.findReceivers(onlineUniqueIds).thenAccept(receivers -> {
            if (receivers.isEmpty()) {
                return;
            }

            this.noticeService.create()
                .players(receivers)
                .noticeOptional(translation -> this.nextAutoMessage(translation.autoMessage()))
                .send();
        });
    }
    private Optional<Notice> nextAutoMessage(AutoMessageMessages messageSection) {
        Collection<Notice> messages = messageSection.messages();

        if (messages.isEmpty()) {
            return Optional.empty();
        }

        if (this.settings.drawMode() == AutoMessageSettings.DrawMode.RANDOM) {
            return RandomElementUtil.randomElement(messages);
        }

        int index = this.broadcastCount.getAndIncrement() % messages.size();
        return messages.stream().skip(index).findFirst();
    }
}
