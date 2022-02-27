/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChatManager {

    private final ConfigurationManager configurationManager;
    @Getter private final Cache<UUID, Long> slowdown;
    @Getter private double chatDelay;
    @Getter private boolean chatEnabled;

    public ChatManager(ConfigurationManager configurationManager) {
        PluginConfiguration.Chat chat = configurationManager.getPluginConfiguration().chat;
        this.configurationManager = configurationManager;

        this.chatDelay = chat.slowMode;
        this.chatEnabled = chat.enabled;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.chatDelay + 10), TimeUnit.SECONDS)
            .build();
    }

    public void useChat(UUID userUuid) {
        this.slowdown.put(userUuid, (long) (System.currentTimeMillis() + this.chatDelay * 1000L));
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;

        this.configurationManager.render(this.configurationManager.getPluginConfiguration());
    }

    public boolean isSlowedOnChat(UUID userUuid) {
        return this.slowdown.asMap().getOrDefault(userUuid, 0L) > System.currentTimeMillis();
    }

    public long getSlowDown(UUID userUuid) {
        return Math.max(this.slowdown.asMap().getOrDefault(userUuid, 0L) - System.currentTimeMillis(), 0L);
    }

    public void setChatDelay(double chatDelay) {
        this.chatDelay = chatDelay;

        this.configurationManager.render(this.configurationManager.getPluginConfiguration());
    }
}
