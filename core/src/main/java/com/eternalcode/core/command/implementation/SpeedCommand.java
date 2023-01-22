package com.eternalcode.core.command.implementation;

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

    @Execute
    @Min(1)
    void execute(CommandSender sender, Viewer audience, @Arg @By("speed") Integer amount, @Arg @By("or_sender") Player player) {
        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        this.noticeService.create()
            .notice(translation -> translation.player().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.player().speedSetBy())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{SPEED}", String.valueOf(amount))
            .viewer(audience)
            .send();
    }
}
