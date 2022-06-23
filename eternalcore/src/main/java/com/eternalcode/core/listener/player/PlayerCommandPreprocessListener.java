package com.eternalcode.core.listener.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.chat.notification.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import panda.std.Option;

public class PlayerCommandPreprocessListener implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public PlayerCommandPreprocessListener(NoticeService noticeService, ConfigurationManager configurationManager, Server server) {
        this.noticeService = noticeService;
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

            this.noticeService
                .create()
                .player(player.getUniqueId())
                .message(messages -> messages.chat().noCommand())
                .placeholder("{COMMAND}", command)
                .send();
        });
    }
}
