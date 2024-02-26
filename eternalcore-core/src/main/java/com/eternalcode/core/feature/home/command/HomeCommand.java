package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.Collection;
import org.bukkit.entity.Player;

@Command(name = "home")
@Permission("eternalcore.home")
class HomeCommand {

    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final HomeManager homeManager;

    @Inject
    HomeCommand(
        TeleportTaskService teleportTaskService,
        TeleportService teleportService,
        NoticeService noticeService,
        HomeManager homeManager) {
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.homeManager = homeManager;
    }

    @Execute
    @DescriptionDocs(description = "Teleports to the first home if the player has no other homes set, if player has eternalcore.teleport.bypass permission, eternalcore will be ignore teleport time")
    void execute(@Context Player player) {
        Collection<Home> playerHomes = this.homeManager.getHomes(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().noHomesOwned())
                .send();
            return;
        }

        if (playerHomes.size() != 1) {
            String homes = String.join(
                ", ",
                this.homeManager.getHomes(player.getUniqueId()).stream()
                    .map(Home::getName)
                    .toList());

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().homeList())
                .placeholder("{HOMES}", homes)
                .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        this.teleportToHome(player, firstHome);
    }

    @Execute
    @DescriptionDocs(description = "Teleport to home, if player has eternalcore.teleport.bypass permission, eternalcore will be ignore teleport time", arguments = "<home>")
    void execute(@Context Player player, @Arg Home home) {
        this.teleportToHome(player, home);
    }

    private void teleportToHome(Player player, Home home) {
        if (player.hasPermission("eterncore.teleport.bypass")) {
            this.teleportService.teleport(player, home.getLocation());
            return;
        }

        // TODO: implement @{link HomeTeleportEvent}, but it's need recode @{link TeleportTaskService} to return CompletableFuture, to be able use @{link CompletableFuture#thenAccept} method
        this.teleportTaskService.createTeleport(
            player.getUniqueId(),
            PositionAdapter.convert(player.getLocation()),
            PositionAdapter.convert(
                home.getLocation()),
            Duration.ofSeconds(5));
    }
}
