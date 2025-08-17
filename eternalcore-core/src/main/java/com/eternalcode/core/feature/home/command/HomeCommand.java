package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.home.HomeTeleportService;
import com.eternalcode.core.feature.home.HomesSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.entity.Player;

@Command(name = "home")
@Permission("eternalcore.home")
class HomeCommand {

    private final HomesSettings homesSettings;
    private final NoticeService noticeService;
    private final HomeService homeService;
    private final HomeTeleportService homeTeleportService;

    @Inject
    HomeCommand(
        HomesSettings homesSettings,
        NoticeService noticeService,
        HomeService homeService,
        HomeTeleportService homeTeleportService
    ) {
        this.homesSettings = homesSettings;
        this.noticeService = noticeService;
        this.homeService = homeService;
        this.homeTeleportService = homeTeleportService;
    }

    @Execute
    @DescriptionDocs(description = "Teleports to the first home if the player has no other homes set, if player has eternalcore.home.bypass permission, eternalcore will ignore teleport time")
    void execute(@Sender Player player) {
        Collection<Home> playerHomes = this.homeService.getHomes(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().noHomesOwned())
                .send();
            return;
        }

        if (playerHomes.size() > 1) {
            String homes = String.join(
                ", ",
                playerHomes.stream()
                    .map(Home::getName)
                    .toList());

            Optional<Home> mainHome = playerHomes.stream()
                .filter(home -> home.getName().equals(this.homesSettings.defaultHomeName()))
                .findFirst();

            if (mainHome.isPresent()) {
                this.homeTeleportService.teleport(player, mainHome.get());
                return;
            }

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().homeList())
                .placeholder("{HOMES}", homes)
                .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        this.homeTeleportService.teleport(player, firstHome);
    }

    @Execute
    @DescriptionDocs(description = "Teleport to home, if player has eternalcore.home.bypass permission, eternalcore will be ignore teleport time", arguments = "<home>")
    void execute(@Sender Player player, @Arg Home home) {
        this.homeTeleportService.teleport(player, home);
    }
}
