package com.eternalcode.core.feature.chat;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class ChatManager {

    private final Cache<UUID, Instant> slowdown;
    private final ChatSettings chatSettings;

    @Inject
    public ChatManager(PluginConfiguration pluginConfiguration) {
        this.chatSettings = pluginConfiguration.chat;
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
