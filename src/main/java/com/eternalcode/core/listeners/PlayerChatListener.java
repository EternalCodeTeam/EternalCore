/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.listeners;

import com.eternalcode.core.command.implementations.ChatCommand;
import com.eternalcode.core.utils.ChatUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (ChatCommand.chatMuted && !player.hasPermission("eternalcore.chat.bypass")) {
            player.sendMessage(ChatUtils.color("Chat jest obecnie wyłączony"));
            event.setCancelled(true);
            return;
        }
    }
}
