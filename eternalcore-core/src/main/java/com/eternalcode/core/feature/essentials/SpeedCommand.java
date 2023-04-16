package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.command.argument.SpeedArgument;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "speed")
@Permission("eternalcore.speed")
public class SpeedCommand {

    private final NoticeService noticeService;

    public SpeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Set speed of walking and flying to specified amount", arguments = "<amount>")
    void execute(Player player, @Arg @By(SpeedArgument.KEY) Integer amount) {
        this.setSpeed(player, amount);

        this.noticeService.create()
            .notice(translation -> translation.player().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    @DescriptionDocs(description = "Set speed of walking and flying to specified amount and player", arguments = "<amount> <player>")
    void execute(Viewer viewer, @Arg @By(SpeedArgument.KEY) Integer amount, @Arg Player target) {
        this.setSpeed(target, amount);

        this.noticeService.create()
            .notice(translation -> translation.player().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.player().speedSetBy())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SPEED}", String.valueOf(amount))
            .viewer(viewer)
            .send();
    }

    void setSpeed(Player player, int amount) {
        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);
    }
}
