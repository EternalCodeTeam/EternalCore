package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "teleport", aliases = { "tp" })
@Permission("eternalcore.command.teleport")
public class TeleportCommand {

    private final NoticeService noticeService;

    public TeleportCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Min(1)
    public void execute(CommandSender sender, Viewer audience, @Arg Player player, @Arg @By("or_sender") Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player playerSender) {
                PaperLib.teleportAsync(playerSender, player.getLocation());

                this.noticeService
                    .notice()
                    .message(messages -> messages.other().successfullyTeleported())
                    .placeholder("{PLAYER}", player.getName())
                    .viewer(audience)
                    .send();

                return;
            }

            this.noticeService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player playerArgument = playerOption.get();
        PaperLib.teleportAsync(player, playerArgument.getLocation());

        this.noticeService
            .notice()
            .message(messages -> messages.other().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{PLAYER-ARG}", player.getName())
            .viewer(audience)
            .send();
    }
}
