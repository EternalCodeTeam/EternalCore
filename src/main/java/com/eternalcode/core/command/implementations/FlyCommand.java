/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "fly")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/fly <player>")
public class FlyCommand {

    private final AudiencesService audiencesService;
    private final PluginConfiguration config;

    public FlyCommand(AudiencesService audiencesService, PluginConfiguration config) {
        this.audiencesService = audiencesService;
        this.config = config;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setAllowFlight(!player.getAllowFlight());

                this.audiencesService.notice()
                    .player(player.getUniqueId())
                    .placeholder("{STATE}", player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled)
                    .message(messages -> messages.other().flyMessage())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        player.setAllowFlight(!player.getAllowFlight());

        this.audiencesService
            .notice()
            .message(messages -> messages.other().flyMessage())
            .placeholder("{STATE}", player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled)
            .player(player.getUniqueId())
            .send();

        this.audiencesService
            .notice()
            .message(messages -> messages.other().flySetMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{STATE}", player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled)
            .sender(sender)
            .send();
    }
}
