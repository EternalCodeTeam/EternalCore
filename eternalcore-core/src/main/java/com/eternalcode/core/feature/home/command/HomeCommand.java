package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.HomesConfig;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.home.HomeTeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Collection;
import java.util.Optional;

import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;

@Command(name = "home")
@Permission("eternalcore.home")
class HomeCommand {

    private final HomesConfig homesConfig;
    private final NoticeService noticeService;
    private final HomeService homeService;
    private final HomeTeleportService homeTeleportService;

    @Inject
    HomeCommand(
        HomesConfig homesConfig,
        NoticeService noticeService,
        HomeService homeService,
        HomeTeleportService homeTeleportService
    ) {
        this.homesConfig = homesConfig;
        this.noticeService = noticeService;
        this.homeService = homeService;
        this.homeTeleportService = homeTeleportService;
    }

    @Execute
    @DescriptionDocs(description = "Teleports to the first home if the player has no other homes set, " +
        "if player has eternalcore.home.bypass permission, eternalcore will ignore teleport time")
    void execute(@Context Player player) {
        Optional<Set<Home>> homesOptional = this.homeService.getHomes(player.getUniqueId());
        if (homesOptional.isEmpty()) {

            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().noHomesOwned())
                .send();
            return;
        }
        Set<Home> homeSet = homesOptional.get();

        Home home = homeSet.stream().findFirst().get();

        this.homeTeleportService.teleport(player, home);
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.home().teleportToHome())
            .placeholder("{HOME}", home.getName())
            .send();

    }

    @Execute
    @DescriptionDocs(description = "Teleport to home, if player has eternalcore.home.bypass permission, eternalcore will be ignore teleport time", arguments = "<home>")
    void execute(@Context Player player, @Arg Home home) {
        this.homeTeleportService.teleport(player, home);
    }
}
