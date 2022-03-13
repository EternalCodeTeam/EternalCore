package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.valid.AmountValidator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "speed")
@Permission("eternalcore.command.speed")
@UsageMessage("&8» &cPoprawne użycie &7/speed <liczba> [gracz]")
public class SpeedCommand {

    private static final AmountValidator SPEED_AMOUNT_VALIDATOR = AmountValidator.NONE.min(0).max(10);

    private final NoticeService noticeService;

    public SpeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, Audience audience, @Arg(0) Integer amount, @Arg(1) @Handler(PlayerArgOrSender.class) Player player) {
        if (!SPEED_AMOUNT_VALIDATOR.valid(amount)) {
            this.noticeService.notice()
                .message(messages -> messages.other().speedBetweenZeroAndTen())
                .audience(audience)
                .send();

            return;
        }

        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        this.noticeService.notice()
            .message(messages -> messages.other().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().speedSetBy())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{SPEED}", String.valueOf(amount))
            .audience(audience)
            .send();
    }
}
