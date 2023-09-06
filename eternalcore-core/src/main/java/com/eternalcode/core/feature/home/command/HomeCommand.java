package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.command.argument.home.ArgHome;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collection;

@Route(name = "home")
@Permission("eternalcore.home")
public class HomeCommand {

    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final HomeManager homeManager;

    @Inject
    public HomeCommand(TeleportTaskService teleportTaskService, TeleportService teleportService, NoticeService noticeService, HomeManager homeManager) {
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.homeManager = homeManager;
    }

    @Execute
    @DescriptionDocs(description = "Teleports to the first home if the player has no other homes set, if player has eternalcore.teleport.bypass permission, eternalcore will be ignore teleport time")
    void execute(Player player) {
        Collection<Home> playerHomes = this.homeManager.getHomes(player.getUniqueId());

        if (playerHomes.size() != 1) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().enterName())
                .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        this.teleportToHome(player, firstHome);
    }

    @Execute
    @DescriptionDocs(description = "Teleport to home, if player has eternalcore.teleport.bypass permission, eternalcore will be ignore teleport time", arguments = "<home>")
    void execute(Player player, @ArgHome Home home) {
        this.teleportToHome(player, home);
    }

    private void teleportToHome(Player player, Home home) {
        if (player.hasPermission("eterncore.teleport.bypass")) {
            this.teleportService.teleport(player, home.getLocation());
            return;
        }

        this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), PositionAdapter.convert(home.getLocation()), Duration.ofSeconds(5));
    }
}
