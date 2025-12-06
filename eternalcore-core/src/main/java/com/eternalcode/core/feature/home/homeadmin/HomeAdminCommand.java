package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import io.papermc.lib.PaperLib;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
    @DescriptionDocs(description = "Set home for user", arguments = "<user> <home> [location]")
    void setHome(@Sender Player sender, @Arg("player") OfflinePlayer offlinePlayer, @Arg("home name") String homeName, @Arg Optional<Location> location) {
        Location optionalLocation = location.orElse(sender.getLocation());

        UUID uniqueId = offlinePlayer.getUniqueId();

        boolean hasHome = this.homeManager.hasHome(uniqueId, homeName);
        this.homeManager.createHome(uniqueId, homeName, optionalLocation);

        if (hasHome) {
            this.noticeService.create()
                .notice(translation -> translation.home().overrideHomeLocationAsAdmin())
                .placeholder("{HOME}", homeName)
                .placeholder("{PLAYER}", offlinePlayer.getName())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.home().createAsAdmin())
            .placeholder("{HOME}", homeName)
            .placeholder("{PLAYER}", offlinePlayer.getName())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(name = "delhome")
    @DescriptionDocs(description = "Delete home for user", arguments = "<user> <home>")
    void deleteHome(@Sender Player sender, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        User user = playerHomeEntry.user();

        UUID uniqueId = user.getUniqueId();
        boolean hasHome = this.homeManager.hasHome(uniqueId, home);

        if (!hasHome) {
            Collection<Home> homes = this.homeManager.getHomes(uniqueId);
            String homesList = this.formattedListUserHomes(homes);

            this.noticeService.create()
                .notice(translation -> translation.home().noHomesOnListAsAdmin())
                .placeholder("{HOMES}", homesList)
                .placeholder("{PLAYER}", user.getName())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        this.homeManager.deleteHome(uniqueId, home.getName());
        this.noticeService.create()
            .notice(translation -> translation.home().deleteAsAdmin())
            .placeholder("{HOME}", home.getName())
            .placeholder("{PLAYER}", user.getName())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(name = "home")
    @DescriptionDocs(description = "Teleport to user home", arguments = "<user> <home>")
    void home(@Sender Player player, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        User user = playerHomeEntry.user();

        Optional<Home> homeOption = this.homeManager.getHome(user.getUniqueId(), home.getName());

        if (homeOption.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.home().playerNoOwnedHomes())
                .placeholder("{HOME}", home.getName())
                .placeholder("{PLAYER}", user.getName())
                .player(player.getUniqueId())
                .send();

            return;
        }

        PaperLib.teleportAsync(player, homeOption.get().getLocation());

        this.noticeService.create()
            .notice(translation -> translation.home().teleportedAsAdmin())
            .placeholder("{HOME}", home.getName())
            .placeholder("{PLAYER}", user.getName())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "List user homes", arguments = "<user>")
    void list(@Sender Viewer viewer, @Arg User user) {

        Collection<Home> homes = this.homeManager.getHomes(user.getUniqueId());

        if (homes.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.home().noHomesOnListAsAdmin())
                .placeholder("{PLAYER}", user.getName())
                .viewer(viewer)
                .send();

            return;
        }

        String homesList = this.formattedListUserHomes(homes);

        this.noticeService.create()
            .notice(translation -> translation.home().homeListAsAdmin())
            .placeholder("{HOMES}", homesList)
            .placeholder("{PLAYER}", user.getName())
            .viewer(viewer)
            .send();
    }

    private String formattedListUserHomes(Collection<Home> homes) {
        return homes.stream()
            .map(home -> home.getName())
            .collect(Collectors.joining(this.pluginConfiguration.format.separator));
    }
}

