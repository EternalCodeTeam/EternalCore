/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.chat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChatManager {

    private final Cache<UUID, Long> slowdown;
    private ChatSettings chatSettings = ChatSettings.NONE;

    public ChatManager() {
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.chatSettings.getChatDelay() + 10), TimeUnit.SECONDS)
            .build();
    }

    public ChatManager(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.chatSettings.getChatDelay() + 10), TimeUnit.SECONDS)
            .build();
    }

    public ChatSettings getChatSettings() {
        return chatSettings;
    }

    public void setChatSettings(ChatSettings chatSettings) {
        this.chatSettings = chatSettings;
    }

    public void markUseChat(UUID userUuid) {
        this.slowdown.put(userUuid, (long) (System.currentTimeMillis() + this.chatSettings.getChatDelay() * 1000L));
    }

    public boolean hasSlowedChat(UUID userUuid) {
        return this.slowdown.asMap().getOrDefault(userUuid, 0L) > System.currentTimeMillis();
    }

    public long getSlowDown(UUID userUuid) {
        return Math.max(this.slowdown.asMap().getOrDefault(userUuid, 0L) - System.currentTimeMillis(), 0L);
    }

}
