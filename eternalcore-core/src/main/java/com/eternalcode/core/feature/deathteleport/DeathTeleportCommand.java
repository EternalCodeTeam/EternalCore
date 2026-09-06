package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "deathteleport", aliases = "deathtp")
@Permission("eternalcore.deathteleport")
class DeathTeleportCommand {

    private final DeathTeleportToggleService toggleService;
    private final NoticeService noticeService;

    @Inject
    DeathTeleportCommand(DeathTeleportToggleService toggleService, NoticeService noticeService) {
        this.toggleService = toggleService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggle automatic teleportation to your last death location after respawning")
    void execute(@Sender Player sender) {
        UUID player = sender.getUniqueId();
        this.toggleService.toggleState(player)
            .thenAccept(state -> this.noticePlayer(state, player));
    }

    @Execute(name = "enable")
    @DescriptionDocs(description = "Enable automatic teleportation to your last death location after respawning")
    void executeEnable(@Sender Player sender) {
        UUID player = sender.getUniqueId();
        this.toggleService.setState(player, DeathTeleportState.ENABLED)
            .thenAccept(ignored -> this.noticePlayer(DeathTeleportState.ENABLED, player));
    }

    @Execute(name = "disable")
    @DescriptionDocs(description = "Disable automatic teleportation to your last death location after respawning")
    void executeDisable(@Sender Player sender) {
        UUID player = sender.getUniqueId();
        this.toggleService.setState(player, DeathTeleportState.DISABLED)
            .thenAccept(ignored -> this.noticePlayer(DeathTeleportState.DISABLED, player));
    }

    @Execute
    @Permission("eternalcore.deathteleport.other")
    @DescriptionDocs(description = "Toggle automatic death teleportation for another player", arguments = "<player>")
    void executeOther(@Sender CommandSender sender, @Arg Player target) {
        UUID player = target.getUniqueId();
        this.toggleService.toggleState(player)
            .thenAccept(state -> this.noticeOtherPlayer(sender, state, target));
    }

    private void noticeOtherPlayer(CommandSender sender, DeathTeleportState state, Player target) {
        this.noticePlayer(state, target.getUniqueId());
        if (sender.equals(target)) {
            return;
        }

        this.noticeService.create()
            .sender(sender)
            .notice(translation -> state == DeathTeleportState.ENABLED
                ? translation.deathTeleport().deathTeleportOtherEnabled()
                : translation.deathTeleport().deathTeleportOtherDisabled())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    private void noticePlayer(DeathTeleportState state, UUID player) {
        this.noticeService.create()
            .player(player)
            .notice(translation -> state == DeathTeleportState.ENABLED
                ? translation.deathTeleport().deathTeleportSelfEnabled()
                : translation.deathTeleport().deathTeleportSelfDisabled())
            .send();
    }
}
