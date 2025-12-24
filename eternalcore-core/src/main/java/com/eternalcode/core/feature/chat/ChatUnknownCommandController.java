package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

@PermissionDocs(
    name = "Bypass Unknown Command Message",
    description = "Bypass replaced unknown command message, and keep the original message.",
    permission = ChatUnknownCommandController.COMMANDBLOCKER_BYPASS
)
@Controller
class ChatUnknownCommandController implements Listener {

    static final String COMMANDBLOCKER_BYPASS = "eternalcore.commandblocker.bypass";

    private final NoticeService noticeService;
    private final ChatSettings chatSettings;
    private final Server server;

    @Inject
    ChatUnknownCommandController(
        NoticeService noticeService,
        ChatSettings chatSettings,
        Server server
    ) {
        this.noticeService = noticeService;
        this.chatSettings = chatSettings;
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

        if (!this.chatSettings.replaceStandardHelpMessage()) {
            return;
        }

        if (player.hasPermission(COMMANDBLOCKER_BYPASS)) {
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
