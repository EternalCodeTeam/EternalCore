package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.god")
public class GodCommand {

    private final NoticeService noticeService;

    public GodCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(CommandSender sender, Viewer viewer, @Arg @By("or_sender") Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.noticeService
            .create()
            .placeholder("{STATE}", messages -> player.isInvulnerable() ? messages.format().enable() : messages.format().disable())
            .message(messages -> messages.other().godMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .create()
            .placeholder("{STATE}", messages -> player.isInvulnerable() ? messages.format().enable() : messages.format().disable())
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().godSetMessage())
            .viewer(viewer)
            .send();
    }
}
