package com.eternalcode.core.feature.back;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Optional;
import org.bukkit.entity.Player;

@Command(name = "back")
class BackCommand {

    private final BackService backService;
    private final NoticeService noticeService;

    @Inject
    public BackCommand(BackService backService, NoticeService noticeService) {
        this.backService = backService;
        this.noticeService = noticeService;
    }


    @Execute
    @Permission("eternalcore.back")
    @DescriptionDocs(description = "Teleport to your last teleport/death location, depending which one is most recent")
    public void executeBack(@Sender Player player) {
        Optional<Position> latestPositionOptional = this.backService.getLatestLocation(player.getUniqueId());
        if (latestPositionOptional.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNotFound());
            return;
        }
        Position latestPosition = latestPositionOptional.get();
        Position deathPosition = this.backService.getDeathLocation(player.getUniqueId()).orElse(null);

        if (latestPosition.equals(deathPosition)) {
            this.executeBackDeath(player);
            return;
        }
        this.executeBackTeleport(player);
    }


    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport")
    @DescriptionDocs(description = "Teleport to your last teleport location")
    public void executeBackTeleport(@Sender Player player) {
        if (this.backService.teleportBack(player)) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastTeleportLocation());
            return;
        }
        this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNotFound());
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death")
    @DescriptionDocs(description = "Teleport to your last death location")
    public void executeBackDeath(@Sender Player player) {
        if (this.backService.teleportBackDeath(player)) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastDeathLocation());
            return;
        }
        this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNotFound());
    }

    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport.other")
    @DescriptionDocs(description = "Teleport specified player to their last teleport location", arguments = "<player>")
    public void executeBackTeleportOther(@Sender Viewer viewer, @Arg Player target) {
        if (!this.backService.teleportBack(target)) {
            this.noticeService.player(viewer.getUniqueId(), translation -> translation.back().lastLocationNotFound());
            return;
        }

        this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastTeleportLocationByAdmin());
        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedTargetPlayerToLastTeleportLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death.other")
    @DescriptionDocs(description = "Teleport specified player to their last death location", arguments = "<player>")
    public void executeBackDeathOther(@Sender Viewer viewer, @Arg Player target) {
        if (!this.backService.teleportBackDeath(target)) {
            this.noticeService.player(viewer.getUniqueId(), translation -> translation.back().lastLocationNotFound());
            return;
        }

        this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastDeathLocationByAdmin());
        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedTargetPlayerToLastDeathLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();

    }
}
