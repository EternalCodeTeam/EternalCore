package com.eternalcode.core.updater;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.commons.updater.UpdateResult;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
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

    private static final Notice UPDATE_NOTICE = Notice.chat(
        "<gradient:#9d6eef:#A1AAFF:#9d6eef>ᴇᴛᴇʀɴᴀʟᴄᴏʀᴇ</gradient> <white>- Update available!</white>",
        " ",
        "<color:#CFCFCF>New version <b>{NEW_VERSION}</b> is ready to download!</color>",
        "<color:#CFCFCF>Download: <click:open_url:'{DOWNLOAD_URL}'><u>{DOWNLOAD_URL}</u></click></color>"
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

        if (!shouldNotifyPlayer(player)) {
            return;
        }

        this.updaterService.checkForUpdate()
            .thenAccept(result -> notifyIfUpdateAvailable(player, result))
            .exceptionally(FutureHandler::handleException);
    }

    private boolean shouldNotifyPlayer(Player player) {
        return player.hasPermission(RECEIVE_UPDATES) && this.pluginConfiguration.shouldReceivePluginUpdates;
    }

    private void notifyIfUpdateAvailable(Player player, UpdateResult result) {
        if (!result.isUpdateAvailable()) {
            return;
        }

        String downloadUrl = result.downloadUrl();
        String string = result.latestVersion().toString();

        this.noticeService.create()
            .notice(UPDATE_NOTICE)
            .player(player.getUniqueId())
            .placeholder("{NEW_VERSION}", downloadUrl)
            .placeholder("{DOWNLOAD_URL}", string)
            .sendAsync();
    }
}
