package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Duration;

@Command(name = "jail")
@FeatureDocs(
    name = "Jail",
    permission = { "eternalcore.jail.setup", "eternalcore.jail.detain", "eternalcore.jail.release", "eternalcore.jail.bypass" },
    description = "Permissions allow for you to setup jail location on your server, detain players, release players, and bypass jail."
)
public class JailCommand {

    private final JailService jailService;
    private final NoticeService noticeService;
    private final PrisonerService prisonerService;

    @Inject
    JailCommand(JailService jailService, NoticeService noticeService, PrisonerService prisonerService) {
        this.jailService = jailService;
        this.noticeService = noticeService;
        this.prisonerService = prisonerService;
    }

    @Execute(name = "setup")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Define jail spawn area")
    void executeJailSetup(@Context Player player) {
        this.noticeService.create()
            .notice(translation -> (this.jailService.isLocationSet() ? translation.jailSection().jailLocationOverride() : translation.jailSection().jailLocationSet()))
            .player(player.getUniqueId())
            .send();

        Location location = player.getLocation();
        this.jailService.setupJailArea(location, player);
    }

    @Execute(name = "setup")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Define jail spawn area", arguments = "<location>")
    void executeJailSetup(@Context Player player, @Arg Location location) {
        this.noticeService.create()
            .notice(translation -> (this.jailService.isLocationSet() ? translation.jailSection().jailLocationOverride() : translation.jailSection().jailLocationSet()))
            .player(player.getUniqueId())
            .send();

        this.jailService.setupJailArea(location, player);
    }

    @Execute(name = "remove")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Remove jail spawn area")
    void executeJailRemove(@Context Player player) {

        if (!this.jailService.isLocationSet()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationNotSet())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailService.removeJailArea(player);

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailLocationRemove())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain self")
    void executeJailDetainSelf(@Context Player player) {
        // Jail self forever
        this.prisonerService.detainPlayer(player,  player, null);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player", arguments = "<player>")
    void executeJailDetain(@Context Player player, @Arg Player target) {
        // Jail player forever
        this.prisonerService.detainPlayer(target,  player, null);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player for some time", arguments = "<player> <time>")
    void executeJailDetainForTime(@Context Player player, @Arg Player target, @Arg Duration duration) {
        // Jail player for a time
        this.prisonerService.detainPlayer(target,  player, duration);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release self from jail")
    void executeJailReleaseSelf(@Context Player player) {
        // Unjail self
        this.prisonerService.releasePlayer(player, player);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release a player from jail", arguments = "<player>")
    void executeJailRelease(@Context Player player, @Arg Player target) {
        // Unjail player
        this.prisonerService.releasePlayer(target, player);
    }

    @Execute(name = "release all", aliases = {"release *"})
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release all players from jail")
    void executeJailReleaseAll(@Context Player player) {
        // Unjail all players
        this.prisonerService.releaseAllPlayers(player);
    }

    @Execute(name = "list")
    @Permission("eternalcore.jail.list")
    @DescriptionDocs(description = "List all jailed players")
    void executeJailList(@Context Player player) {
        // List all jailed players
        this.prisonerService.listJailedPlayers(player);
    }
}
