package com.eternalcode.core.chat;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.DateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class ChatManager {

    @Getter private final Cache<UUID, Long> chatCache;
    @Getter private boolean                 enabled;
    private final char[]                    clearMessage;
    private final ConfigurationManager      configManager;

    public ChatManager(ConfigurationManager manager){
        this.configManager = manager;

        this.chatCache = CacheBuilder.newBuilder()
            .expireAfterWrite(configManager.getPluginConfiguration().chatSlowMode * 1000L, TimeUnit.SECONDS)
            .build();
        this.enabled = true;
        this.clearMessage = new char[30000];
        Arrays.fill(this.clearMessage, ' ');
    }

    public void clearChat(CommandSender sender){
        MessagesConfiguration messages = this.configManager.getMessagesConfiguration();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(String.valueOf(clearMessage));
            Bukkit.broadcast(ChatUtils.component(messages.chatCleared.replace("{NICK}", sender.getName())));

        });
    }

    public void switchChat(CommandSender sender, boolean statue){
        MessagesConfiguration messages = this.configManager.getMessagesConfiguration();
        if (statue){
            if (this.enabled){
                sender.sendMessage(ChatUtils.color(messages.chatAlreadyEnabled));
                return;
            }
            this.enabled = true;
            Bukkit.broadcast(ChatUtils.component(messages.chatEnabled.replace("{NICK}", sender.getName())));
            return;
        }
        if (!this.enabled){
            sender.sendMessage(ChatUtils.color(messages.chatAlreadyDisabled));
            return;
        }
        this.enabled = false;
        Bukkit.broadcast(ChatUtils.component(messages.chatDisabled.replace("{NICK}", sender.getName())));
    }

    public void slowMode(CommandSender sender, String slowMode){
        MessagesConfiguration messages = this.configManager.getMessagesConfiguration();
        PluginConfiguration config = this.configManager.getPluginConfiguration();
        if (!ChatUtils.isNumber(slowMode)){
            sender.sendMessage(ChatUtils.color(messages.notNumber));
            return;
        }
        config.chatSlowMode = Integer.parseInt(slowMode);
        sender.sendMessage(ChatUtils.color(messages.chatSlowModeSet.replace("{SLOWMODE}", slowMode)));
    }

    public void useChat(AsyncChatEvent event){
        MessagesConfiguration messages = this.configManager.getMessagesConfiguration();
        PluginConfiguration config = this.configManager.getPluginConfiguration();

        Player player = event.getPlayer();

        if (!this.enabled && !player.hasPermission("enernalCore.chat.bypass")){
            player.sendMessage(ChatUtils.color(messages.chatDisable));
            event.setCancelled(true);
            return;
        }

        Option<Long> chat = Option.of(this.chatCache.getIfPresent(player.getUniqueId()));
        if (!player.hasPermission("enernalCore.chat.noslowmode") && chat.isPresent() && chat.get() > System.currentTimeMillis()){
            player.sendMessage(ChatUtils.color(messages.chatSlowMode.replace("{TIME}", DateUtils.durationToString(chat.get()))));
            event.setCancelled(true);
            return;
        }
        this.chatCache.put(player.getUniqueId(), System.currentTimeMillis() + config.chatSlowMode * 1000L);
    }

}
