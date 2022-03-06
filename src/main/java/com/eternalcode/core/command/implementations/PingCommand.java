package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.PermissionExclude;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "ping")
@PermissionExclude("eternalcore.command.ping")
@UsageMessage("&8» &cPoprawne użycie &7/ping [player]")
public class PingCommand {

    private final AudiencesService audiencesService;

    public PingCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                this.audiencesService
                    .notice()
                    .message(messages -> messages.other().pingMessage())
                    .placeholder("{PING}", String.valueOf(player.getPing()))
                    .player(player.getUniqueId())
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        this.audiencesService
            .notice()
            .message(messages -> messages.other().pingOtherMessage())
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{PLAYER}", player.getName())
            .sender(sender)
            .send();
    }
}
