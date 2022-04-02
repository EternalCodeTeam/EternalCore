package com.eternalcode.core.chat;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.UUID;

public class ChatManager {

    private final Cache<UUID, Long> slowdown;
    private final ChatSettings chatSettings;

    public ChatManager(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
        this.slowdown = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMillis((long) (this.chatSettings.getChatDelay() * 1000L)))
            .build();
    }

    public void markUseChat(UUID userUuid) {
        this.slowdown.put(userUuid, (long) (System.currentTimeMillis() + this.chatSettings.getChatDelay() * 1000L));
    }

    public boolean hasSlowedChat(UUID userUuid) {
        return this.slowdown.get(userUuid, key -> 0L) > System.currentTimeMillis();
    }

    public long getSlowDown(UUID userUuid) {
        return Math.max(this.slowdown.get(userUuid, key -> 0L) - System.currentTimeMillis(), 0L);
    }

}
