package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
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
import io.papermc.lib.PaperLib;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "homeadmin")
@Permission("eternalcore.home.admin")
class HomeAdminCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    public HomeAdminCommand(
        HomeManager homeManager,
        NoticeService noticeService,
        PluginConfiguration pluginConfiguration
    ) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(name = "sethome")
    @DescriptionDocs(description = "Set home for user", arguments = "<user> <home> [position]")
    void setHome(@Context Player sender, @Arg("player home") PlayerHomeEntry playerHomeEntry, @Arg Optional<Location> location) {
        Location optionalLocation = location.orElse(sender.getLocation());

        Home home = playerHomeEntry.home();
        Player player = playerHomeEntry.player();
        UUID uniqueId = player.getUniqueId();

        boolean hasHome = this.homeManager.hasHome(uniqueId, home);
        String name = home.getName();

        Position position = PositionAdapter.convert(optionalLocation);

        if (hasHome) {
            this.homeManager.createHome(uniqueId, name, position);
            this.noticeService.create()
                .notice(translate -> translate.home().overrideHomeLocationAsAdmin())
                .placeholder("{HOME}", name)
                .placeholder("{PLAYER}", player.getName())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.homeManager.createHome(uniqueId, name, position);
        this.noticeService.create()
            .notice(translate -> translate.home().createAsAdmin())
            .placeholder("{HOME}", name)
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "delhome")
    @DescriptionDocs(description = "Delete home for user", arguments = "<user> <home>")
    void deleteHome(@Context Player sender, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        Player player = playerHomeEntry.player();

        UUID uniqueId = player.getUniqueId();
        boolean hasHome = this.homeManager.hasHome(uniqueId, home);

        if (!hasHome) {
            String homes = this.formattedListUserHomes(uniqueId);

            this.noticeService.create()
                .notice(translate -> translate.home().homeList())
                .placeholder("{HOMES}", homes)
                .placeholder("{PLAYER}", player.getName())
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
    void home(@Context Player player, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        Player user = playerHomeEntry.player();

        Optional<Home> homeOption = this.homeManager.getHome(user.getUniqueId(), home.getName());

        if (homeOption.isEmpty()) {
            this.noticeService.create()
                .notice(translate -> translate.home().playerNoOwnedHomes())
                .placeholder("{HOME}", home.getName())
                .placeholder("{PLAYER}", user.getName())
                .player(player.getUniqueId())
                .send();

            return;
        }

        Location homeLocation = PositionAdapter.convert(homeOption.get().getPosition());
        PaperLib.teleportAsync(player, homeLocation);
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "List user homes", arguments = "<user>")
    void list(@Context Viewer viewer, @Arg User user) {
        String homes = this.formattedListUserHomes(user.getUniqueId());

        this.noticeService.create()
            .notice(translate -> translate.home().homeListAsAdmin())
            .placeholder("{HOMES}", homes)
            .placeholder("{PLAYER}", user.getName())
            .viewer(viewer)
            .send();
    }

    private String formattedListUserHomes(UUID uniqueId) {
        return this.homeManager.getHomes(uniqueId).stream()
            .map(Home::getName)
            .collect(Collectors.joining(this.pluginConfiguration.format.separator));
    }
}

