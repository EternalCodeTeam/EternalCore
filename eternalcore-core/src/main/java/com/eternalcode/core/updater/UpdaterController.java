package com.eternalcode.core.updater;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@PermissionDocs(
    name = "Receive Updates",
    permission = UpdaterController.RECEIVE_UPDATES,
    description = "Sends a message to the player when a new plugin update is available after joining the server."
)
@Controller
class UpdaterController implements Listener {

    static final String RECEIVE_UPDATES = "eternalcore.receiveupdates";

    private static final Notice UPDATE_MESSAGES = Notice.chat(
        "",
        " <gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore Update Available</gradient>",
        "    <i><gray>New version is ready to download</gray></i>",
        " ",
        " Download new version from:",
        "  <click:open_url:'https://modrinth.com/plugin/eternalcore'><hover:show_text:'Modrinth'><b><color:#00ffcc>• "
            + "Modrinth</color></b></hover></click>",
        "  <click:open_url:'https://hangar.papermc.io/EternalCodeTeam/EternalCore'><hover:show_text:'Hangar'><b><color"
            + ":#00ffcc>• Hangar</color></b></hover></click>",
        "  <click:open_url:'https://www.spigotmc.org/resources/eternalcore-%E2%99%BE%EF%B8%8F-all-the-most-important-server-functions-in-one.112264/'><hover:show_text:'SpigotMC'><b><color:#00ffcc>• SpigotMC</color></b></hover></click>",
        ""
    );

    private final PluginConfiguration pluginConfiguration;
    private final UpdaterService updaterService;
    private final NoticeService noticeService;

    @Inject
    UpdaterController(
        PluginConfiguration pluginConfiguration,
        UpdaterService updaterService,
        NoticeService noticeService
    ) {
        this.pluginConfiguration = pluginConfiguration;
        this.updaterService = updaterService;
        this.noticeService = noticeService;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(RECEIVE_UPDATES)) {
            return;
        }

        if (!this.pluginConfiguration.shouldReceivePluginUpdates) {
            return;
        }

        CompletableFuture<Boolean> future = this.updaterService.isUpToDate();

        future.whenComplete((upToDate, throwable) -> {
            if (throwable != null) {
                return;
            }

            if (!upToDate) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(UPDATE_MESSAGES)
                    .sendAsync();
            }
        });
    }
}
