package com.eternalcode.core.listeners.player;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import panda.std.Option;

public class PlayerCommandPreprocessListener implements Listener {

    private final MessagesConfiguration message;
    private final PluginConfiguration config;

    public PlayerCommandPreprocessListener(MessagesConfiguration message, PluginConfiguration config) {
        this.message = message;
        this.config = config;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        Option<HelpTopic> helpTopic = Option.of(Bukkit.getHelpMap().getHelpTopic(command));

        helpTopic.onEmpty(() -> {

            if (!config.commandExact) {
                return;
            }

            if (player.hasPermission("eternalcode.commandblocker.bypass")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(ChatUtils.color(StringUtils.replace(message.messagesSection.chatNoCommand, "{COMMAND}", command)));
        });
    }
}
