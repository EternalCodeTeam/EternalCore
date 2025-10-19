package com.eternalcode.core.feature.speed;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
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
    void execute(@Sender Player player, @Arg(SpeedArgument.KEY) Double speed) {
        this.setSpeed(player, speed);

        this.noticeService.create()
            .notice(translation -> player.isFlying()
                ? translation.speed().flySpeedSet()
                : translation.speed().walkSpeedSet())
            .placeholder("{SPEED}", String.valueOf(speed))
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed depending on whether the player is flying or walking by specified amount", arguments = "<speed> <player>")
    void execute(@Sender Viewer viewer, @Arg(SpeedArgument.KEY) Double speed, @Arg Player target) {
        this.setSpeed(target, speed);

        this.noticeService.create()
            .notice(translation -> target.isFlying()
                ? translation.speed().flySpeedSet()
                : translation.speed().walkSpeedSet())
            .placeholder("{SPEED}", String.valueOf(speed))
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> target.isFlying()
                ? translation.speed().flySpeedSetForTargetPlayer()
                : translation.speed().walkSpeedSetForTargetPlayer())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SPEED}", String.valueOf(speed))
            .viewer(viewer)
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed of walking or flying to specified amount", arguments = "<type> <speed>")
    void execute(@Sender Player player, @Arg SpeedType speedType, @Arg(SpeedArgument.KEY) Double speed) {
        this.setSpeed(player, speedType, speed);

        this.noticeService.create()
            .notice(translation -> speedType == SpeedType.WALK
                ? translation.speed().walkSpeedSet()
                : translation.speed().flySpeedSet())
            .placeholder("{SPEED}", String.valueOf(speed))
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Set speed of walking or flying to specified amount and player", arguments = "<type> <speed> <player>")
    void execute(
        @Sender Viewer viewer,
        @Arg SpeedType speedType,
        @Arg(SpeedArgument.KEY) Double speed,
        @Arg Player target) {
        this.setSpeed(target, speedType, speed);

        this.noticeService.create()
            .notice(translation -> speedType == SpeedType.WALK
                ? translation.speed().walkSpeedSet()
                : translation.speed().flySpeedSet())
            .placeholder("{SPEED}", String.valueOf(speed))
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> speedType == SpeedType.WALK
                ? translation.speed().walkSpeedSetForTargetPlayer()
                : translation.speed().flySpeedSetForTargetPlayer())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SPEED}", String.valueOf(speed))
            .viewer(viewer)
            .send();
    }

    void setSpeed(Player player, double speed) {
        float scaled = (float) (speed / 10.0);
        if (player.isFlying()) {
            player.setFlySpeed(scaled);
        }
        else {
            player.setWalkSpeed(scaled);
        }
    }

    void setSpeed(Player player, SpeedType speedType, double speed) {
        float scaled = (float) (speed / 10.0);
        switch (speedType) {
            case WALK -> player.setWalkSpeed(scaled);
            case FLY -> player.setFlySpeed(scaled);
            default -> throw new IllegalStateException("Unexpected value: " + speedType);
        }
    }
}
