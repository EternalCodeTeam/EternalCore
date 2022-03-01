/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
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
import panda.std.Option;

@Section(route = "speed")
@Permission("eternalcore.command.speed")
@UsageMessage("&8» &cPoprawne użycie &7/speed <liczba> [gracz]")
public class SpeedCommand {

    private static final AmountValidator SPEED_AMOUNT_VALIDATOR = AmountValidator.NONE.min(0).max(10);

    private final AudiencesService audiencesService;

    public SpeedCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Arg(0) Integer amount, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (!SPEED_AMOUNT_VALIDATOR.valid(amount)) {
            this.audiencesService
                .notice()
                .message(messages -> messages.other().speedBetweenZeroAndTen())
                .sender(sender)
                .send();

            return;
        }

        if (playerOption.isEmpty()) {
            if (sender instanceof Player player){
                player.setWalkSpeed(amount / 10.0f);
                player.setFlySpeed(amount / 10.0f);

                this.audiencesService
                    .notice()
                    .message(messages -> messages.other().speedSet())
                    .placeholder("{SPEED}", String.valueOf(amount))
                    .player(player.getUniqueId())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        this.audiencesService
            .notice()
            .message(messages -> messages.other().speedSet())
            .placeholder("{SPEED}", String.valueOf(amount))
            .player(player.getUniqueId())
            .send();

        this.audiencesService
            .notice()
            .message(messages -> messages.other().speedSetBy())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{SPEED}", String.valueOf(amount))
            .sender(sender)
            .send();
    }
}
