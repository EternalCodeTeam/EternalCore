package com.eternalcode.core.feature.chat;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
class ChatManagerServiceImpl implements ChatManagerService {

    private final Cache<UUID, Instant> slowdown;
    private final ChatSettings chatSettings;

    @Inject
    ChatManagerServiceImpl(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite(this.chatSettings.getChatDelay().plus(Duration.ofSeconds(10)))
            .build();
    }

    @Override
    public ChatSettings getChatSettings() {
        return this.chatSettings;
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
