package com.eternalcode.core.feature.jail;

import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_BYPASS_PERMISSION;
import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_COMMAND_PERMISSION;
import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_DETAIN_PERMISSION;
import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_LIST_PERMISSION;
import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_RELEASE_PERMISSION;
import static com.eternalcode.core.feature.jail.JailPermissionConstant.JAIL_SETUP_PERMISSION;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Command(name = "jail")
@Permission(JAIL_COMMAND_PERMISSION)
class JailCommand {

    private final JailService jailService;
    private final NoticeService noticeService;
    private final JailSettings jailSettings;
    private final Server server;

    @Inject
    JailCommand(JailService jailService, NoticeService noticeService, JailSettings jailSettings, Server server) {
        this.jailService = jailService;
        this.noticeService = noticeService;
        this.jailSettings = jailSettings;
        this.server = server;
    }

    @Async
    @Execute(name = "setup")
    @Permission(JAIL_SETUP_PERMISSION)
    @DescriptionDocs(description = "Define jail spawn area")
    void executeJailSetup(@Sender Player player) {
        Location location = player.getLocation();

        boolean isLastJailSet = this.jailService.getJailAreaLocation().isPresent();
        this.jailService.setupJailArea(location);

        this.noticeService.create()
            .notice(translation -> (isLastJailSet
                ? translation.jail().locationOverride()
                : translation.jail().locationSet()))
            .player(player.getUniqueId())
            .send();
    }

    @Async
    @Execute(name = "setup")
    @Permission(JAIL_SETUP_PERMISSION)
    @DescriptionDocs(description = "Define jail spawn area", arguments = "<location>")
    void executeJailSetup(@Sender Player player, @Arg Location location) {
        boolean isLastJailSet = this.jailService.getJailAreaLocation().isPresent();

        location.setWorld(player.getWorld());
        this.jailService.setupJailArea(location);

        this.noticeService.create()
            .notice(translation -> (isLastJailSet
                ? translation.jail().locationOverride()
                : translation.jail().locationSet()))
            .player(player.getUniqueId())
            .send();
    }

    @Async
    @Execute(name = "remove")
    @Permission(JAIL_SETUP_PERMISSION)
    @DescriptionDocs(description = "Remove jail spawn area")
    void executeJailRemove(@Sender Player player) {
        if (this.handleMissingJailLocation(player)) {
            return;
        }

        this.jailService.removeJailArea();

        this.noticeService.create()
            .notice(translation -> translation.jail().locationRemoved())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "detain")
    @Permission(JAIL_DETAIN_PERMISSION)
    @DescriptionDocs(description = "Detain self")
    void executeJailDetainSelf(@Sender Player player) {
        this.executeJailDetainForTime(player, player, this.jailSettings.defaultJailDuration());
    }

    @Execute(name = "detain")
    @Permission(JAIL_DETAIN_PERMISSION)
    @DescriptionDocs(description = "Detain a player", arguments = "<player>")
    void executeJailDetain(@Sender Player player, @Arg Player target) {
        this.executeJailDetainForTime(player, target, this.jailSettings.defaultJailDuration());
    }

    @Execute(name = "detain")
    @Permission(JAIL_DETAIN_PERMISSION)
    @DescriptionDocs(description = "Detain a player for some time", arguments = "<player> <time>")
    void executeJailDetainForTime(@Sender Player player, @Arg Player target, @Arg Duration duration) {
        if (this.handleMissingJailLocation(player)) {
            return;
        }

        if (target.hasPermission(JAIL_BYPASS_PERMISSION)) {
            this.noticeService.create()
                .notice(translation -> translation.jail().detainAdmin())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        boolean isPlayerJailed = this.jailService.isPlayerJailed(target.getUniqueId());

        if (isPlayerJailed) {
            this.noticeService.create()
                .notice(translation -> translation.jail().detainOverride())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
        }

        this.jailService.detainPlayer(target, player, duration);

        this.noticeService.create()
            .notice(translation -> translation.jail().detainBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jail().detained())
            .player(target.getUniqueId())
            .send();
    }

    @Execute(name = "release")
    @Permission(JAIL_RELEASE_PERMISSION)
    @DescriptionDocs(description = "Release self from jail")
    void executeJailReleaseSelf(@Sender Player player) {
        this.executeJailRelease(player, player);
    }

    @Execute(name = "release")
    @Permission(JAIL_RELEASE_PERMISSION)
    @DescriptionDocs(description = "Release a player from jail", arguments = "<player>")
    void executeJailRelease(@Sender Player player, @Arg Player target) {
        if (!this.jailService.isPlayerJailed(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jail().isNotPrisoner())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailService.releasePlayer(target);

        this.noticeService.create()
            .notice(translation -> translation.jail().released())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jail().releaseBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .all()
            .send();
    }

    @Execute(name = "release -all", aliases = {"release *"})
    @Permission(JAIL_RELEASE_PERMISSION)
    @DescriptionDocs(description = "Release all players from jail")
    void executeJailReleaseAll(@Sender Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jail().releaseNoPlayers())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailService.releaseAllPlayers();

        this.noticeService.create()
            .notice(translation -> translation.jail().releaseAll())
            .all()
            .send();
    }

    @Execute(name = "list")
    @Permission(JAIL_LIST_PERMISSION)
    @DescriptionDocs(description = "List all jailed players")
    void executeJailList(@Sender Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jail().listEmpty())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jail().listHeader())
            .player(player.getUniqueId())
            .send();

        for (JailedPlayer jailedPlayer : this.jailService.getJailedPlayers()) {
            this.noticeService.create()
                .notice(translation -> translation.jail().listPlayerEntry())
                .placeholder("{PLAYER}", this.server.getOfflinePlayer(jailedPlayer.playerUniqueId()).getName())
                .placeholder("{REMAINING_TIME}", DurationUtil.format(jailedPlayer.remainingTime(), true))
                .placeholder("{DETAINED_BY}", jailedPlayer.detainedBy())
                .player(player.getUniqueId())
                .send();
        }
    }

    private boolean handleMissingJailLocation(Player player) {
        if (this.jailService.getJailAreaLocation().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jail().locationNotSet())
                .player(player.getUniqueId())
                .send();
            return true;
        }

        return false;
    }

}
