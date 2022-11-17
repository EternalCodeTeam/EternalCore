package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.AmountValidator;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "speed")
@Permission("eternalcore.speed")
public class SpeedCommand {

    private static final AmountValidator SPEED_AMOUNT_VALIDATOR = AmountValidator.NONE.min(0).max(10);

    private final NoticeService noticeService;

    public SpeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Min(1)
    void execute(CommandSender sender, Viewer audience, @Arg Integer amount, @Arg @By("or_sender") Player player) {
        if (!SPEED_AMOUNT_VALIDATOR.valid(amount)) {
            this.noticeService.create()
                .message(messages -> messages.other().speedBetweenZeroAndTen())
                .viewer(audience)
                .send();

            return;
        }

        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        this.noticeService.create()
            .message(messages -> messages.other().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .message(messages -> messages.other().speedSetBy())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{SPEED}", String.valueOf(amount))
            .viewer(audience)
            .send();
    }
}
