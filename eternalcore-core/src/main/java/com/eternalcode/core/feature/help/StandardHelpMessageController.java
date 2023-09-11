package com.eternalcode.core.feature.help;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

@FeatureDocs(
    name = "Standard help message",
    description = "It allows you to replace the standard help message",
    permission = "eternalcore.commandblocker.bypass"
)
@Controller
class StandardHelpMessageController implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    @Inject
    StandardHelpMessageController(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        HelpTopic helpTopic = this.server.getHelpMap().getHelpTopic(command);

        if (helpTopic != null) {
            return;
        }

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
    }
}
