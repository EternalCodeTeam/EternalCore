package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.option.Opt;
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

    @Execute(required = 2)
    public void to(Viewer sender, @Arg Player player, @Arg Player target) {
        PaperLib.teleportAsync(player, target.getLocation());

        this.noticeService.notice()
            .message(messages -> messages.other().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{PLAYER-ARG}", player.getName())
            .viewer(sender)
            .send();
    }

    @Execute(required = 1)
    public void execute(Player sender, Viewer senderViewer, @Arg Player player) {
        PaperLib.teleportAsync(sender, player.getLocation());

        this.noticeService.notice()
            .message(messages -> messages.other().successfullyTeleported())
            .placeholder("{PLAYER}", player.getName())
            .viewer(senderViewer)
            .send();
    }
}
