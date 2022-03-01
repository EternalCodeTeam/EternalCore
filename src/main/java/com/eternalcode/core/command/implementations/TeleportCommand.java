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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "tp", aliases = { "teleport" })
@Permission("eternalcore.command.tp")
@UsageMessage("&8» &cPoprawne użycie &7/teleport <player> [player]")
public class TeleportCommand {

    private final AudiencesService audiencesService;

    public TeleportCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Arg(0) Player player, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player playerSender) {
                playerSender.teleportAsync(player.getLocation());

                this.audiencesService
                    .notice()
                    .message(messages -> messages.other().successfullyTeleported())
                    .placeholder("{PLAYER}", player.getName())
                    .sender(sender)
                    .send();

                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player playerArgument = playerOption.get();

        player.teleportAsync(playerArgument.getLocation());

        this.audiencesService
            .notice()
            .message(messages -> messages.other().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{PLAYER-ARG}", player.getName())
            .sender(sender)
            .send();
    }
}
