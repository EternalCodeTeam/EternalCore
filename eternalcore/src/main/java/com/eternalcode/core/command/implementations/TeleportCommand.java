package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArg;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
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

    private final NoticeService noticeService;

    public TeleportCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, Audience audience, @Arg(0) Player player, @Arg(1) @Handler(PlayerArgOrSender.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player playerSender) {
                playerSender.teleportAsync(player.getLocation());

                this.noticeService
                    .notice()
                    .message(messages -> messages.other().successfullyTeleported())
                    .placeholder("{PLAYER}", player.getName())
                    .audience(audience)
                    .send();

                return;
            }

            this.noticeService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player playerArgument = playerOption.get();

        player.teleportAsync(playerArgument.getLocation());

        this.noticeService
            .notice()
            .message(messages -> messages.other().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{PLAYER-ARG}", player.getName())
            .audience(audience)
            .send();
    }
}
