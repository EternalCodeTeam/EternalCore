package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "jail")
public class JailCommand {

    private final JailService jailService;
    private final NoticeService noticeService;
    @Inject
    JailCommand(JailService jailService, NoticeService noticeService) {
        this.jailService = jailService,
        this.noticeService = noticeService;
    }

    @Execute(name = "setup")
    @Permission("eternalcore.jail.setup")
    @DescriptionDocs(description = "Define jail spawn area", arguments = "<location>")
    void executeJailSetup(@Context Player player, @Arg Location location) {

        if (this.jailService.isLocationSet()) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.jailService().jailLocationOverride());

        }

        this.jailService.setupJailArea(location);

        // Create jail area
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain self")
    void executeJailDetainSelf(@Context Player player) {
        // Jail self forever
        this.jailService.detainPlayer(player, null, player);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player", arguments = "<player>")
    void executeJailDetain(@Context Player player, @Arg Player target) {
        // Jail player forever
        this.jailService.detainPlayer(target);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player with a reason", arguments = "<player> <reason>")
    void executeJailDetainWithReason(@Context Player player, @Arg Player target, @Arg String reason) {
        // Jail player forever with a reason
        this.jailService.detainPlayer(target, reason);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player for a time", arguments = "<player> <time>")
    void executeJailDetainForTime(@Context Player player, @Arg Player target, @Arg String time) {
        // Jail player for a time
        this.jailService.detainPlayer(target, time);
    }

    @Execute(name = "detain")
    @Permission("eternalcore.jail.detain")
    @DescriptionDocs(description = "Detain a player for a time with a reason", arguments = "<player> <time> <reason>")
    void executeJailDetainForTimeWithReason(@Context Player player, @Arg Player target, @Arg String time, @Arg String reason) {
        // Jail player for a time with a reason
        this.jailService.detainPlayer(target, time, reason);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release a player from jail", arguments = "<player>")
    void executeJailRelease(@Context Player player, @Arg Player target) {
        // Unjail player
        this.jailService.releasePlayer(target);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release self from jail")
    void executeJailReleaseSelf(@Context Player player) {
        // Unjail self
        this.jailService.releasePlayer(player);
    }

    @Execute(name = "release")
    @Permission("eternalcore.jail.release")
    @DescriptionDocs(description = "Release all players from jail")
    void executeJailReleaseAll(@Context Player player) {
        // Unjail all players
        this.jailService.releaseAllPlayers();
    }


}
