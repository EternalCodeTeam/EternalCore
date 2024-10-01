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

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@FeatureDocs(
    description = "Check if a player has permission to use a specific warp",
    name = "Warp permission"
)
@Controller
public class WarpPermissionController implements Listener {

    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    public WarpPermissionController(NoticeService noticeService, PluginConfiguration pluginConfiguration) {
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    void onWarpPreTeleportation(PreWarpTeleportEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        Warp warp = event.getWarp();

        this.checkWarpPermission(event, warp, player, uniqueId);
    }

    private void checkWarpPermission(PreWarpTeleportEvent event, Warp warp, Player player, UUID uniqueId) {
        Map<String, Set<String>> warpPermissions = this.pluginConfiguration.warp.warpPermissions;

        if (!warpPermissions.containsKey(warp.getName())) {
            return;
        }

        Set<String> permissions = warpPermissions.get(warp.getName());
        Optional<String> isPlayerAllowedToUseWarp = permissions
            .stream()
            .filter(player::hasPermission)
            .findAny();

        if (isPlayerAllowedToUseWarp.isPresent()) {
            return;
        }

        event.setCancelled(true);

        this.noticeService.create()
            .player(uniqueId)
            .placeholder("{WARP}", warp.getName())
            .notice(translation -> translation.warp().noPermission())
            .send();
    }

}
