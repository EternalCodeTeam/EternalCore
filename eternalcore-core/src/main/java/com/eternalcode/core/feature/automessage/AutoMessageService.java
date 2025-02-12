package com.eternalcode.core.feature.automessage;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.feature.automessage.messages.AutoMessageMessages;
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

@FeatureDocs(name = "AutoMessage", description = "Automatically sends messages to players at a given time interval.")
@Service
class AutoMessageService {

    private final AutoMessageRepository repository;
    private final AutoMessageSettings settings;
    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Scheduler scheduler;
    private final Server server;

    private final AtomicInteger broadcastCount = new AtomicInteger(0);

    @Inject
    AutoMessageService(
        AutoMessageRepository repository,
        AutoMessageSettings settings,
        NoticeService noticeService,
        PluginConfiguration config,
        Scheduler scheduler,
        Server server
    ) {
        this.repository = repository;
        this.settings = settings;
        this.noticeService = noticeService;
        this.config = config;
        this.scheduler = scheduler;
        this.server = server;

        this.tick();
    }

    CompletableFuture<Boolean> switchReceiving(UUID uniqueId) {
        return this.repository.switchReceiving(uniqueId);
    }

    public void broadcastNextMessage() {
        Set<UUID> onlineUniqueIds = this.server.getOnlinePlayers().stream()
            .map(Entity::getUniqueId)
            .collect(Collectors.toSet());

        int requiredReceivers = this.config.autoMessage.minPlayers;
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

    private void tick() {
        this.scheduler.runLaterAsync(this::tick, this.settings.interval());

        if (this.settings.enabled()) {
            this.broadcastNextMessage();
        }
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
