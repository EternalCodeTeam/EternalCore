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
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "feed")
@UsageMessage("&8» &cPoprawne użycie &7/feed <player>")
@Permission("eternalcore.command.fly")
public class FeedCommand {

    private final AudiencesService audiencesService;

    public FeedCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setFoodLevel(20);
                this.audiencesService.sender(sender, messages -> messages.other().foodMessage());
                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }
        Player player = playerOption.get();

        player.setFoodLevel(20);

        this.audiencesService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.other().foodMessage())
            .send();

        this.audiencesService
            .notice()
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().foodOtherMessage())
            .sender(sender)
            .send();
    }
}

