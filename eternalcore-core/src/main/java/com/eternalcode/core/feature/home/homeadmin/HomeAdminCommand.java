package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
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

    private static final String CLICK_SUGGEST_COMMAND_FORMATTED_LIST = "<click:run_command:'/homeadmin home %s %s'>%s</click>";

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
    void setHome(
        @Sender Player sender,
        @Arg("target") OfflinePlayer targetPlayer,
        @Arg("home") String homeName,
        @Arg Optional<Location> location
    ) {
        if (!this.hasPlayerEverJoined(targetPlayer)) {
            this.sendPlayerNeverJoinedNotice(sender, targetPlayer);
            return;
        }

        Location homeLocation = location.orElse(sender.getLocation());
        UUID targetId = targetPlayer.getUniqueId();
        boolean homeExists = this.homeManager.hasHome(targetId, homeName);

        this.homeManager.createHome(targetId, homeName, homeLocation);

        if (homeExists) {
            this.sendHomeOverrideNotice(sender.getUniqueId(), homeName, targetPlayer);
        }
        else {
            this.sendHomeCreatedNotice(sender.getUniqueId(), homeName, targetPlayer);
        }
    }

    @Execute(name = "delhome")
    @DescriptionDocs(description = "Delete home for user", arguments = "<user> <home>")
    void deleteHome(@Sender Player sender, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        OfflinePlayer targetPlayer = playerHomeEntry.offlinePlayer();
        UUID targetId = targetPlayer.getUniqueId();

        this.homeManager.deleteHome(targetId, home.getName());

        this.noticeService.create()
            .notice(translation -> translation.home().deleteAsAdmin())
            .placeholder("{HOME}", home.getName())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(name = "home")
    @DescriptionDocs(description = "Teleport to user home", arguments = "<user> <home>")
    void home(@Sender Player player, @Arg("player home") PlayerHomeEntry playerHomeEntry) {
        Home home = playerHomeEntry.home();
        OfflinePlayer targetPlayer = playerHomeEntry.offlinePlayer();

        PaperLib.teleportAsync(player, home.getLocation());

        this.noticeService.create()
            .notice(translation -> translation.home().teleportedAsAdmin())
            .placeholder("{HOME}", home.getName())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "List user homes", arguments = "<user>")
    void list(@Sender Viewer viewer, @Arg("target") OfflinePlayer targetPlayer) {
        if (!this.hasPlayerEverJoined(targetPlayer)) {
            this.sendPlayerNeverJoinedNoticeToViewer(viewer, targetPlayer);
            return;
        }

        UUID targetId = targetPlayer.getUniqueId();
        Collection<Home> homes = this.homeManager.getHomes(targetId);

        if (homes.isEmpty()) {
            this.sendNoHomesNotice(viewer, targetPlayer);
            return;
        }

        this.sendHomeListNotice(viewer, homes, targetPlayer);
    }

    private boolean hasPlayerEverJoined(OfflinePlayer player) {
        return player.hasPlayedBefore() || player.isOnline();
    }

    private void sendPlayerNeverJoinedNotice(Player sender, OfflinePlayer targetPlayer) {
        this.noticeService.create()
            .notice(translation -> translation.argument().offlinePlayer())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .player(sender.getUniqueId())
            .send();
    }

    private void sendPlayerNeverJoinedNoticeToViewer(Viewer viewer, OfflinePlayer targetPlayer) {
        this.noticeService.create()
            .notice(translation -> translation.argument().offlinePlayer())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .viewer(viewer)
            .send();
    }

    private void sendHomeOverrideNotice(UUID senderId, String homeName, OfflinePlayer targetPlayer) {
        this.noticeService.create()
            .notice(translation -> translation.home().overrideHomeLocationAsAdmin())
            .placeholder("{HOME}", homeName)
            .placeholder("{PLAYER}", targetPlayer.getName())
            .player(senderId)
            .send();
    }

    private void sendHomeCreatedNotice(UUID senderId, String homeName, OfflinePlayer targetPlayer) {
        this.noticeService.create()
            .notice(translation -> translation.home().createAsAdmin())
            .placeholder("{HOME}", homeName)
            .placeholder("{PLAYER}", targetPlayer.getName())
            .player(senderId)
            .send();
    }

    private void sendNoHomesNotice(Viewer viewer, OfflinePlayer targetPlayer) {
        this.noticeService.create()
            .notice(translation -> translation.home().noHomesOnListAsAdmin())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .viewer(viewer)
            .send();
    }

    private void sendHomeListNotice(Viewer viewer, Collection<Home> homes, OfflinePlayer targetPlayer) {
        String playerName = targetPlayer.getName();
        String formattedHomes = this.formatHomeList(homes, playerName);

        this.noticeService.create()
            .notice(translation -> translation.home().homeListAsAdmin())
            .placeholder("{HOMES}", formattedHomes)
            .placeholder("{PLAYER}", playerName)
            .viewer(viewer)
            .send();
    }

    private String formatHomeList(Collection<Home> homes, String playerName) {
        return homes.stream()
            .map(home -> String.format(
                CLICK_SUGGEST_COMMAND_FORMATTED_LIST,
                playerName,
                home.getName(),
                home.getName()
            ))
            .collect(Collectors.joining(
                this.pluginConfiguration.format.separator
            ));
    }
}
