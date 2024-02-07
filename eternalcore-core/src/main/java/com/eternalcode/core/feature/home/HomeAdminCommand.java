package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
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
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

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
    void setHome(@Context Player player, @Arg User user, @Arg String home, @Arg Optional<Location> location) {
        Location optionalLocation = location.orElse(player.getLocation());

        boolean hasHome = this.homeManager.hasHomeWithSpecificName(user, home);

        if (hasHome) {
            this.homeManager.createHome(user, home, optionalLocation);
            this.noticeService.create()
                .notice(translate -> translate.home().overrideHomeLocationAsAdmin())
                .placeholder("{HOME}", home)
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.homeManager.createHome(user, home, optionalLocation);
        this.noticeService.create()
            .notice(translate -> translate.home().createAsAdmin())
            .placeholder("{HOME}", home)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "delhome")
    @DescriptionDocs(description = "Delete home for user", arguments = "<user> <home>")
    void deleteHome(@Context Player player, @Arg User user, @Arg String name) {
        boolean hasHome = this.homeManager.hasHomeWithSpecificName(user, name);

        if (!hasHome) {
            String homes = this.formattedListUserHomes(user);

            this.noticeService.create()
                .notice(translate -> translate.home().homeList())
                .placeholder("{HOMES}", homes)
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.homeManager.deleteHome(user, name);
    }

    @Execute(name = "home")
    @DescriptionDocs(description = "Teleport to user home", arguments = "<user> <home>")
    void home(@Context Player player, @Arg User user, @Arg String home) {
        Option<Home> homeOption = this.homeManager.getHome(user.getUniqueId(), home);

        if (homeOption.isEmpty()) {
            return;
        }

        Home homeObject = homeOption.get();
        player.teleport(homeObject.getLocation());
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "List user homes", arguments = "<user>")
    void list(@Context Viewer viewer, @Arg User user) {
        String homes = this.formattedListUserHomes(user);

        this.noticeService.create()
            .notice(translate -> translate.home().homeListAsAdmin())
            .placeholder("{HOMES}", homes)
            .viewer(viewer)
            .send();
    }

    private String formattedListUserHomes(User user) {
        return this.homeManager.getHomes(user.getUniqueId()).stream()
            .map(home -> home.getName())
            .collect(Collectors.joining(", "));
    }
}

