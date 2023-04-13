package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@FeatureDocs(
    name = "ChatManager",
    description = "It allows you to manage chat"
)
public class ChatManager {

    private final Cache<UUID, Instant> slowdown;
    private final ChatSettings chatSettings;

    public ChatManager(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite(this.chatSettings.getChatDelay().plus(Duration.ofSeconds(10)))
            .build();
    }

    public ChatSettings getChatSettings() {
        return this.chatSettings;
    }

    public void markUseChat(UUID userUuid) {
        this.slowdown.put(userUuid, Instant.now().plus(this.chatSettings.getChatDelay()));
    }

    public boolean hasSlowedChat(UUID userUuid) {
        return Instant.now().isBefore(this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }

    public Duration getSlowDown(UUID userUuid) {
        return Duration.between(Instant.now(), this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }

}
