/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.managers.chat;

import com.eternalcode.core.configuration.PluginConfiguration;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChatManager {
    private final Cache<UUID, Long> slowdown;
    private double chatDelay;
    private boolean chatEnabled;

    public ChatManager(PluginConfiguration config) {

        this.chatDelay = config.chatSlowMode;
        this.chatEnabled = config.chatStatue;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.chatDelay + 10), TimeUnit.SECONDS)
            .build();
    }

    public void useChat(UUID userUuid) {
        this.slowdown.put(userUuid, (long) (System.currentTimeMillis() + this.chatDelay * 1000L));
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public boolean isSlowedOnChat(UUID userUuid) {
        return slowdown.asMap().getOrDefault(userUuid, 0L) > System.currentTimeMillis();
    }

    public long getSlowDown(UUID userUuid) {
        return Math.max(slowdown.asMap().getOrDefault(userUuid, 0L) - System.currentTimeMillis(), 0L);
    }

    public double getChatDelay() {
        return chatDelay;
    }

    public void setChatDelay(double chatDelay) {
        this.chatDelay = chatDelay;
    }

    public Map<UUID, Long> getSlowdown() {
        return Collections.unmodifiableMap(this.slowdown.asMap());
    }

}
