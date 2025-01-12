package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.warp.event.PreWarpTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FeatureDocs(
    description = "Check if a player has permission to use a specific warp",
    name = "Warp permission"
)
@Controller
public class WarpPermissionController implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration config;

    @Inject
    public WarpPermissionController(NoticeService noticeService, PluginConfiguration config) {
        this.noticeService = noticeService;
        this.config = config;
    }

    @EventHandler
    void onWarpPreTeleportation(PreWarpTeleportEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        Warp warp = event.getWarp();

        this.checkWarpPermission(event, warp, player, uniqueId);
    }

    private void checkWarpPermission(PreWarpTeleportEvent event, Warp warp, Player player, UUID uniqueId) {
        List<String> permissions = warp.getPermissions();
        Optional<String> isPlayerAllowedToUseWarp = permissions
            .stream()
            .filter(player::hasPermission)
            .findAny();

        if (isPlayerAllowedToUseWarp.isPresent() || permissions.isEmpty()) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.create()
            .player(uniqueId)
            .placeholder("{WARP}", warp.getName())
            .placeholder("{PERMISSIONS}", String.join(this.config.format.separator, permissions))
            .notice(translation -> translation.warp().noPermission())
            .send();
    }

}
