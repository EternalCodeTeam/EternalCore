package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "tphere", aliases = { "s" })
@Permission("eternalcore.tphere")
public class TpHereCommand {

    private final NoticeService noticeService;

    public TpHereCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    public void tpHere(Player sender, @Arg Player target) {
        PaperLib.teleportAsync(target, sender.getLocation());

        this.noticeService.notice()
            .message(messages -> messages.teleport().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{ARG-PLAYER}", sender.getName())
            .player(sender.getUniqueId())
            .send();
    }

}
