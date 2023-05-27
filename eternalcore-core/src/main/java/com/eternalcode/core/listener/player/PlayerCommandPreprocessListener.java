package com.eternalcode.core.listener.player;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import panda.std.Option;

@FeatureDocs(
    name = "CommandBlocker",
    description = "It allows you to block commands with custom message",
    permission = "eternalcore.commandblocker.bypass"
)
public class PlayerCommandPreprocessListener implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public PlayerCommandPreprocessListener(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;
    }


    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        Option<HelpTopic> helpTopic = Option.of(this.server.getHelpMap().getHelpTopic(command));

        helpTopic.onEmpty(() -> {
            if (!this.config.chat.replaceStandardHelpMessage) {
                return;
            }

            if (player.hasPermission("eternalcore.commandblocker.bypass")) {
                return;
            }

            event.setCancelled(true);

            this.noticeService
                .create()
                .player(player.getUniqueId())
                .notice(translation -> translation.chat().commandNotFound())
                .placeholder("{COMMAND}", command)
                .send();
        });
    }
}
