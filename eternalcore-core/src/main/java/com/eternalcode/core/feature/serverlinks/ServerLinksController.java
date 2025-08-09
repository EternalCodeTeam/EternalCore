package com.eternalcode.core.feature.serverlinks;

import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
@Compatibility(from = @Version(minor = 21, patch = 0))
public class ServerLinksController implements Listener {

    private final ServerLinksService serverLinksService;
    private final ServerLinksSettings serverLinksSettings;

    @Inject
    public ServerLinksController(
        ServerLinksService serverLinksService,
        ServerLinksSettings serverLinksSettings
    ) {
        this.serverLinksService = serverLinksService;
        this.serverLinksSettings = serverLinksSettings;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.serverLinksSettings.sendLinksOnJoin()) {
            this.serverLinksService.sendServerLinks(event.getPlayer());
        }
    }
}
