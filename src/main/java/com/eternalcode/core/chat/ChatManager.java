package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class ChatManager {
    private final Cache<UUID, Long> slowdown;
    private double chatDelay;
    private boolean chatEnabled;

    public ChatManager(ConfigurationManager configManager) {
        PluginConfiguration config = configManager.getPluginConfiguration();

        this.chatDelay = config.chatSlowMode;
        this.chatEnabled = config.chatStatue;
        this.slowdown = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.chatDelay + 10), TimeUnit.SECONDS)
            .build();
    }

    public void useChat(Player player) {
        this.slowdown.put(player.getUniqueId(), (long) (System.currentTimeMillis() + this.chatDelay * 1000L));
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public boolean isSlowedOnChat(Player player) {
        return slowdown.asMap().getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis();
    }

    public long getSlowDown(Player player) {
        return Math.max(slowdown.asMap().getOrDefault(player.getUniqueId(), 0L) - System.currentTimeMillis(), 0L);
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
