package com.eternalcode.core.feature.sleep;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Controller
@PermissionDocs(
    name = "Phantom Spawn Ignore",
    permission = "eternalcore.sleep.noinsomnia",
    description = "Player with this permission will not spawn phantoms"
)
class PhantomSpawnController implements Listener {

    @EventHandler
    void onPhantomSpawnAttempt(PhantomPreSpawnEvent event) {
        if (!(event.getSpawningEntity() instanceof Player player)) {
            return;
        }

        if (player.hasPermission("eternalcore.sleep.noinsomnia")) {
            event.setCancelled(true);
        }
    }
}
