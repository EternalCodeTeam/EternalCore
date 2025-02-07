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
    private final ServerLinksConfig serverLinksConfig;

    @Inject
    public ServerLinksController(ServerLinksService serverLinksService, ServerLinksConfig serverLinksConfig) {
        this.serverLinksService = serverLinksService;
        this.serverLinksConfig = serverLinksConfig;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.serverLinksConfig.sendLinksOnJoin) {
            this.serverLinksService.sendServerLinks(event.getPlayer());
        }
    }
}
