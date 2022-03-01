/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "stonecutter")
@Permission("eternalcore.command.workbench")
public class StonecutterCommand {

    private final AudiencesService audiencesService;

    public StonecutterCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.openStonecutter(null, true);
                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();
        player.openStonecutter(null, true);
    }
}

