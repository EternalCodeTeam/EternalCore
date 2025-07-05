package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Command(name = "jail")
@PermissionDocs(
    name = "Jail Bypass",
    permission = JailCommand.JAIL_BYPASS,
    description = "Permission allows to bypass jail punishment"
)
class JailCommand {

    static final String JAIL_BYPASS = "eternalcore.jail.bypass";

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
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Define jail spawn area")
    void executeJailSetup(@Context Player player) {
        Location location = player.getLocation();

        boolean isLastJailSet = this.jailService.getJailAreaLocation().isPresent();
        this.jailService.setupJailArea(location);

        this.noticeService.create()
            .notice(translation -> (isLastJailSet
                ? translation.jailSection().jailLocationOverride()
                : translation.jailSection().jailLocationSet()))
            .player(player.getUniqueId())
            .send();
    }

    @Async
    @Execute(name = "setup")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Define jail spawn area", arguments = "<location>")
    void executeJailSetup(@Context Player player, @Arg Location location) {
        boolean isLastJailSet = this.jailService.getJailAreaLocation().isPresent();

        location.setWorld(player.getWorld());
        this.jailService.setupJailArea(location);

        this.noticeService.create()
            .notice(translation -> (isLastJailSet
                ? translation.jailSection().jailLocationOverride()
                : translation.jailSection().jailLocationSet()))
            .player(player.getUniqueId())
            .send();
    }

    @Async
    @Execute(name = "remove")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Remove jail spawn area")
    void executeJailRemove(@Context Player player) {
        if (this.isPrisonAvailable(player)) {
            return;
        }

        this.jailService.removeJailArea();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailLocationRemove())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain self")
    void executeJailDetainSelf(@Context Player player) {
        this.executeJailDetainForTime(player, player, this.jailSettings.defaultJailDuration());
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player", arguments = "<player>")
    void executeJailDetain(@Context Player player, @Arg Player target) {
        this.executeJailDetainForTime(player, target, this.jailSettings.defaultJailDuration());
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player for some time", arguments = "<player> <time>")
    void executeJailDetainForTime(@Context Player player, @Arg Player target, @Arg Duration duration) {
        if (this.isPrisonAvailable(player)) {
            return;
        }

        if (target.hasPermission(JAIL_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainAdmin())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        boolean isPlayerJailed = this.jailService.isPlayerJailed(target.getUniqueId());

        if (isPlayerJailed) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainOverride())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
        }

        this.jailService.detainPlayer(target, player, duration);

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainPrivate())
            .player(target.getUniqueId())
            .send();
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release self from jail")
    void executeJailReleaseSelf(@Context Player player) {
        this.executeJailRelease(player, player);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release a player from jail", arguments = "<player>")
    void executeJailRelease(@Context Player player, @Arg Player target) {
        if (!this.jailService.isPlayerJailed(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailIsNotPrisoner())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailService.releasePlayer(target);

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleasePrivate())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleaseBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .all()
            .send();
    }

    @Execute(name = "release -all", aliases = { "release *" })
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release all players from jail")
    void executeJailReleaseAll(@Context Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailReleaseNoPlayers())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailService.releaseAllPlayers();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleaseAll())
            .all()
            .send();
    }

    @Execute(name = "list")
    @Permission("eternalcore.jail.list")
    @DescriptionDocs(description = "List all jailed players")
    void executeJailList(@Context Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailListEmpty())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailListHeader())
            .player(player.getUniqueId())
            .send();

        for (JailedPlayer jailedPlayer : this.jailService.getJailedPlayers()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailListPlayerEntry())
                .placeholder("{PLAYER}", this.server.getOfflinePlayer(jailedPlayer.getPlayerUniqueId()).getName())
                .placeholder("{REMAINING_TIME}", DurationUtil.format(jailedPlayer.getRemainingTime(), true))
                .placeholder("{DETAINED_BY}", jailedPlayer.getDetainedBy())
                .player(player.getUniqueId())
                .send();
        }
    }

    private boolean isPrisonAvailable(Player player) {
        if (this.jailService.getJailAreaLocation().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationNotSet())
                .player(player.getUniqueId())
                .send();
            return true;
        }

        return false;
    }

}
