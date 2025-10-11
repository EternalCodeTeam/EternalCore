package com.eternalcode.core.feature.back;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.execute.ExecuteDefault;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "back")
public class BackCommand {

    private final BackService backService;
    private final NoticeService noticeService;

    @Inject
    public BackCommand(BackService backService, NoticeService noticeService) {
        this.backService = backService;
        this.noticeService = noticeService;
    }

    @ExecuteDefault
    @Permission("eternalcore.back.teleport")
    void executeDefault(@Sender Player player) {
        this.teleportBack(player);
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death")
    @DescriptionDocs(description = "Teleport to your last death location")
    public void executeBackDeath(@Sender Player player) {
        this.teleportBackDeath(player);
    }

    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport")
    @DescriptionDocs(description = "Teleport to your last teleport location")
    public void executeBackTeleport(@Sender Player player) {
        this.teleportBack(player);
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death.other")
    @DescriptionDocs(description = "Teleport specified player to their last death location", arguments = "<player>")
    public void executeBackDeathOther(@Sender Viewer viewer, @Arg Player target) {
        this.teleportBackDeath(target);

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedOtherToLastDeathLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport.other")
    @DescriptionDocs(description = "Teleport specified player to their last teleport location", arguments = "<player>")
    public void executeBackTeleportOther(@Sender Viewer viewer, @Arg Player target) {
        this.teleportBack(target);

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedOtherToLastTeleportLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    private void teleportBack(Player player) {
        this.backService.getTeleportLocation(player.getUniqueId())
            .ifPresentOrElse(position -> {
                this.backService.teleportBack(player, PositionAdapter.convert(position));
                this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastTeleportLocation());
            }, () -> this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNotFound()));
    }

    private void teleportBackDeath(Player target) {
        this.backService.getDeathLocation(target.getUniqueId())
            .ifPresentOrElse(deathPosition -> {
                this.backService.teleportBack(target, PositionAdapter.convert(deathPosition));
                this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastDeathLocation());
            }, () -> this.noticeService.player(target.getUniqueId(), translation -> translation.back().lastLocationNotFound()));
    }
}
