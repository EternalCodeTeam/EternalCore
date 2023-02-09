package com.eternalcode.core.feature.essentials;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "speed")
@Permission("eternalcore.speed")
public class SpeedCommand {

    private final NoticeService noticeService;

    public SpeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    void execute(Player player, @Arg @By("speed") Integer amount) {
        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        this.noticeService.create()
            .notice(translation -> translation.player().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    void execute(Viewer viewer, @Arg @By("speed") Integer amount, @Arg Player target) {
        target.setFlySpeed(amount / 10.0f);
        target.setWalkSpeed(amount / 10.0f);

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
}
