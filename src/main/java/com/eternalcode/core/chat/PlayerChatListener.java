<<<<<<< Updated upstream
/*
 * Copyright (c) 2021. EternalCode.pl
 */

=======
>>>>>>> Stashed changes
package com.eternalcode.core.chat;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.DateUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import panda.std.Option;

public class PlayerChatListener implements Listener {

    private final EternalCore eternalCore;

    public PlayerChatListener(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        MessagesConfiguration messages = this.eternalCore.getConfigurationManager().getMessagesConfiguration();

        Player player = event.getPlayer();

        if (!this.eternalCore.getChatManager().isEnabled() && !player.hasPermission("enernalCore.chat.bypass")){
            player.sendMessage(ChatUtils.color(messages.chatDisable));
            event.setCancelled(true);
            return;
        }

        Option<Long> chat = Option.of(this.eternalCore.getChatManager().getChatCache().get(player.getUniqueId()));
        if (!player.hasPermission("enernalCore.chat.noslowmode") && chat.isPresent() && chat.get() > System.currentTimeMillis()){
            player.sendMessage(ChatUtils.color(messages.chatSlowMode.replace("{TIME}", DateUtils.durationToString(chat.get()))));
            event.setCancelled(true);
            return;
        }

        this.eternalCore.getChatManager().useChat(player);
    }
}
