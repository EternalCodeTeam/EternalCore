package com.eternalcode.core.feature.sleep;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
@PermissionDocs(
    name = "Sleep Ignore",
    permission = "eternalcore.sleepignore",
    description = "Allows the player holding the permission to be ignored while sleeping in the night"
)
public class PlayerJoinSleepController implements Listener {

    private static final String SLEEP_IGNORE_PERMISSION = "eternalcore.sleep.ignore";

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission(SLEEP_IGNORE_PERMISSION)) {
            event.getPlayer().setSleepingIgnored(true);
        }
    }
}
