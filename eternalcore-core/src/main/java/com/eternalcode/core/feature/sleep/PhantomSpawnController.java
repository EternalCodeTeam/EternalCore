package com.eternalcode.core.feature.sleep;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.paper.phantom.PhantomEventInitializer;
import com.eternalcode.paper.phantom.PhantomSpawnAttemptEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

@Controller
@PermissionDocs(
    name = "Phantom Spawn Ignore",
    permission = "eternalcore.sleep.noinsomnia",
    description = "Allows the player holding the permission to not spawn phantoms when not sleeping for 3+ nights"
)
public class PhantomSpawnController implements Listener {

    private static final String NO_INSOMNIA_PERMISSION = "eternalcore.sleep.noinsomnia";

    @Inject
    public PhantomSpawnController(Plugin plugin) {
        new PhantomEventInitializer(plugin).initialize();
    }

    @EventHandler
    void onPhantomSpawnAttempt(PhantomSpawnAttemptEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasPermission(NO_INSOMNIA_PERMISSION)) {
                event.setCancelled(true);
            }
        }
    }
}
