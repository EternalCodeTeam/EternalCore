package com.eternalcode.core.feature.essentials.speed;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "speed")
@Permission("eternalcore.speed")
class SpeedCommand {

    private final NoticeService noticeService;

    @Inject
    SpeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Set speed depending on whether you flying or walking by specified amount", arguments = "<speed>")
    void execute(@Context Player player, @Arg(SpeedArgument.KEY) Integer speed) {
        this.setSpeed(player, speed);

        this.noticeService.create()
                .notice(translation -> player.isFlying() ? translation.player().speedFlySet() : translation.player().speedWalkSet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .player(player.getUniqueId())
                .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed depending on whether the player is flying or walking by specified amount", arguments = "<speed> <player>")
    void execute(@Context Viewer viewer, @Arg(SpeedArgument.KEY) Integer speed, @Arg Player target) {
        this.setSpeed(target, speed);

        this.noticeService.create()
                .notice(translation -> target.isFlying() ? translation.player().speedFlySet() : translation.player().speedWalkSet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .player(target.getUniqueId())
                .send();

        this.noticeService.create()
                .notice(translation -> target.isFlying() ? translation.player().speedFlySetBy() : translation.player().speedWalkSetBy())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{SPEED}", String.valueOf(speed))
                .viewer(viewer)
                .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed of walking or flying to specified amount", arguments = "<type> <speed>")
    void execute(@Context Player player, @Arg SpeedType speedType, @Arg(SpeedArgument.KEY) Integer speed) {
        this.setSpeed(player, speedType, speed);

        this.noticeService.create()
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSet() : translation.player().speedFlySet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .player(player.getUniqueId())
                .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed of walking or flying to specified amount and player", arguments = "<type> <speed> <player>")
    void execute(@Context Viewer viewer, @Arg SpeedType speedType, @Arg(SpeedArgument.KEY) Integer speed, @Arg Player target) {
        this.setSpeed(target, speedType, speed);

        this.noticeService.create()
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSet() : translation.player().speedFlySet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .player(target.getUniqueId())
                .send();

        this.noticeService.create()
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSetBy() : translation.player().speedFlySetBy())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{SPEED}", String.valueOf(speed))
                .viewer(viewer)
                .send();
    }

    void setSpeed(Player player, int speed) {
        if (player.isFlying()) {
            player.setFlySpeed(speed / 10.0f);
        }
        else {
            player.setWalkSpeed(speed / 10.0f);
        }
    }

    void setSpeed(Player player, SpeedType speedType, int speed) {
        switch (speedType) {
            case WALK -> player.setWalkSpeed(speed / 10.0f);
            case FLY -> player.setFlySpeed(speed / 10.0f);
            default -> throw new IllegalStateException("Unexpected value: " + speedType);
        }
    }
}
