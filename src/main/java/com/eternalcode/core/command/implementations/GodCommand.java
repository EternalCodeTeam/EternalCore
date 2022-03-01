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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.command.god")
public class GodCommand {

    private final AudiencesService audiencesService;
    private final PluginConfiguration config;

    public GodCommand(AudiencesService audiencesService, PluginConfiguration configuration) {
        this.config = configuration;
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setInvulnerable(!player.isInvulnerable());

                this.audiencesService
                    .notice()
                    .placeholder("{STATE}", player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)
                    .message(messages -> messages.other().godMessage())
                    .player(player.getUniqueId())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        player.setInvulnerable(!player.isInvulnerable());

        this.audiencesService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)
            .message(messages -> messages.other().godMessage())
            .player(player.getUniqueId())
            .send();

        this.audiencesService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().godSetMessage())
            .sender(sender)
            .send();
    }
}
