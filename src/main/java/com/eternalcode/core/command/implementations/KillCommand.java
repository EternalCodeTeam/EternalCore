/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "kill")
@Permission("eternalcore.command.kill")
@UsageMessage("&8» &cPoprawne użycie &7/kill <player>")
public class KillCommand {

    private final AudiencesService audiencesService;

    public KillCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler( PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                killPlayer(player);
                return;
            }
            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }
        Player player = playerOption.get();

        killPlayer(player);

        this.audiencesService
            .notice()
            .message(messages -> messages.other().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .sender(sender)
            .send();
    }

    @IgnoreMethod
    private void killPlayer(Player player) {
        player.setHealth(0);

        this.audiencesService
            .notice()
            .message(messages -> messages.other().killSelf())
            .send();
    }

}
