package com.eternalcode.core.listeners.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import panda.std.Option;

public class PlayerCommandPreprocessListener implements Listener {

    private final MessagesConfiguration message;
    private final PluginConfiguration config;
    private final Server server;

    public PlayerCommandPreprocessListener(ConfigurationManager configurationManager, Server server) {
        this.message = configurationManager.getMessagesConfiguration();
        this.config = configurationManager.getPluginConfiguration();
        this.server = server;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        Option<HelpTopic> helpTopic = Option.of(this.server.getHelpMap().getHelpTopic(command));

        helpTopic.onEmpty(() -> {

            if (!this.config.chat.commandExact) {
                return;
            }

            if (player.hasPermission("eternalcode.commandblocker.bypass")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(ChatUtils.color(StringUtils.replace(message.chatSection.noCommand, "{COMMAND}", command)));
        });
    }
}
