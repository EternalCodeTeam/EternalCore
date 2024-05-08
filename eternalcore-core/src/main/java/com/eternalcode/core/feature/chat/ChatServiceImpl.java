package com.eternalcode.core.feature.chat;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
class ChatServiceImpl implements ChatService {

    private final ChatSettings chatSettings;
    private final Cache<UUID, Instant> slowdown;

    @Inject
    ChatServiceImpl(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
        this.slowdown = Caffeine.newBuilder()
            .expireAfterWrite(this.chatSettings.getChatDelay().plus(Duration.ofSeconds(10)))
            .build();
    }

    @Override
    public void markUseChat(UUID userUuid) {
        Duration chatDelay = this.chatSettings.getChatDelay();

        this.slowdown.put(userUuid, Instant.now().plus(chatDelay));
    }

    @Override
    public boolean hasSlowedChat(UUID userUuid) {
        return Instant.now().isBefore(this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }

    @Override
    public Duration getRemainingSlowDown(UUID userUuid) {
        Instant unlockMoment = this.slowdown.asMap().get(userUuid);

        if (unlockMoment == null) {
            return Duration.ZERO;
        }

        return Duration.between(Instant.now(), unlockMoment);
    }

}
