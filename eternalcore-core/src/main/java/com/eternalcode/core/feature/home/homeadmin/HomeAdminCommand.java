package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "homeadmin")
@Permission("eternalcode.home.admin")
class HomeAdminCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    public HomeAdminCommand(HomeManager homeManager, NoticeService noticeService) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Execute(name = "sethome")
    @DescriptionDocs(description = "Set home for user", arguments = "<user> <home> [location]")
    void setHome(@Context Player sender, @Arg PlayerHomeEntry playerHomeEntry, @Arg Optional<Location> location) {
        Location optionalLocation = location.orElse(sender.getLocation());

        Home home = playerHomeEntry.home();
        Player player = playerHomeEntry.player();
        UUID uniqueId = player.getUniqueId();

        boolean hasHome = this.homeManager.hasHome(uniqueId, home);
        String name = home.getName();

        if (hasHome) {
            this.homeManager.createHome(uniqueId, name, optionalLocation);
            this.noticeService.create()
                .notice(translate -> translate.home().overrideHomeLocationAsAdmin())
                .placeholder("{HOME}", name)
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.homeManager.createHome(uniqueId, name, optionalLocation);
        this.noticeService.create()
            .notice(translate -> translate.home().createAsAdmin())
            .placeholder("{HOME}", name)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "delhome")
    @DescriptionDocs(description = "Delete home for user", arguments = "<user> <home>")
    void deleteHome(@Context Player sender, @Arg PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        Player player = playerHomeEntry.player();

        UUID uniqueId = player.getUniqueId();
        boolean hasHome = this.homeManager.hasHome(uniqueId, home);

        if (!hasHome) {
            String homes = this.formattedListUserHomes(uniqueId);

            this.noticeService.create()
                .notice(translate -> translate.home().homeList())
                .placeholder("{HOMES}", homes)
                .player(sender.getUniqueId())
                .send();

            return;
        }

        this.homeManager.deleteHome(uniqueId, home.getName());
        this.noticeService.create()
            .notice(translate -> translate.home().deleteAsAdmin())
            .placeholder("{HOME}", home.getName())
            .placeholder("{PLAYER}", player.getName())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(name = "home")
    @DescriptionDocs(description = "Teleport to user home", arguments = "<user> <home>")
    void home(@Context Player player, @Arg PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        Player user = playerHomeEntry.player();

        Optional<Home> homeOption = this.homeManager.getHome(user.getUniqueId(), home.getName());

        if (homeOption.isEmpty()) {
            this.noticeService.create()
                .notice(translate -> translate.home().playerNoOwnedHomes())
                .placeholder("{HOME}", home.getName())
                .player(player.getUniqueId())
                .send();

            return;
        }

        player.teleport(homeOption.get().getLocation());
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "List user homes", arguments = "<user>")
    void list(@Context Viewer viewer, @Arg User user) {
        String homes = this.formattedListUserHomes(user.getUniqueId());

        this.noticeService.create()
            .notice(translate -> translate.home().homeListAsAdmin())
            .placeholder("{HOMES}", homes)
            .viewer(viewer)
            .send();
    }

    private String formattedListUserHomes(UUID uniqueId) {
        return this.homeManager.getHomes(uniqueId).stream()
            .map(home -> home.getName())
            .collect(Collectors.joining(", "));
    }
}

