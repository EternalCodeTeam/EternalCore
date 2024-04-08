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
        this.slowdown.put(userUuid, Instant.now().plus(this.chatSettings.getChatDelay()));
    }

    @Override
    public boolean hasSlowedChat(UUID userUuid) {
        return Instant.now().isBefore(this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }

    @Override
    public Duration getSlowDown(UUID userUuid) {
        return Duration.between(Instant.now(), this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }

}
