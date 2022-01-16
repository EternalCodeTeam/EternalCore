package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class ChatManager {
    private final ConfigurationManager configManager;
    private final Cache<UUID, Long> chatCache;
    private boolean enabled;

    public ChatManager(ConfigurationManager manager) {
        this.configManager = manager;

        PluginConfiguration config = configManager.getPluginConfiguration();

        this.enabled = config.chatStatue;

        this.chatCache = CacheBuilder.newBuilder()
            .expireAfterWrite(config.chatSlowMode * 1000L, TimeUnit.SECONDS)
            .build();
    }

    public void slowMode(CommandSender sender, String slowMode) {
        MessagesConfiguration messages = this.configManager.getMessagesConfiguration();
        PluginConfiguration config = this.configManager.getPluginConfiguration();

        config.chatSlowMode = Integer.parseInt(slowMode);
        sender.sendMessage(ChatUtils.color(messages.chatSlowModeSet.replace("{SLOWMODE}", slowMode)));
    }

    public void useChat(Player player) {
        PluginConfiguration config = configManager.getPluginConfiguration();

        this.chatCache.put(player.getUniqueId(), System.currentTimeMillis() + config.chatSlowMode * 1000L);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        PluginConfiguration config = this.configManager.getPluginConfiguration();

        this.enabled = enabled;
        config.chatStatue = enabled;
    }

    public Map<UUID, Long> getChatCache() {
        return Collections.unmodifiableMap(this.chatCache.asMap());
    }
}
