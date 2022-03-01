/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "gamemode", aliases = { "gm" })
@Permission("eternalcore.command.gamemode")
@UsageMessage("&8» &cPoprawne użycie &7/gamemode <0/1/2/3> <player>")
public class GamemodeCommand {

    private final AudiencesService audiencesService;

    public GamemodeCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(CommandSender sender, @Arg(0) GameMode gameMode, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setGameMode(gameMode);

                this.audiencesService
                    .notice()
                    .message(messages -> messages.other().gameModeMessage())
                    .placeholder("{GAMEMODE}", gameMode.name())
                    .player(player.getUniqueId())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }
        Player player = playerOption.get();

        player.setGameMode(gameMode);

        this.audiencesService
            .notice()
            .message(messages -> messages.other().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(player.getUniqueId())
            .send();

        this.audiencesService
            .notice()
            .message(messages -> messages.other().gameModeSetMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .placeholder("{PLAYER}", player.getName())
            .sender(sender)
            .send();
    }

}
